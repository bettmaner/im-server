package edu.ncu.zww.imserver.service.socket;

import edu.ncu.zww.imserver.bean.*;
import edu.ncu.zww.imserver.common.util.ApplicationContextUtil;
import edu.ncu.zww.imserver.common.util.MyDate;
import edu.ncu.zww.imserver.common.util.MyObjectInputStream;
import edu.ncu.zww.imserver.controller.Server;
import edu.ncu.zww.imserver.service.GroupService;
import edu.ncu.zww.imserver.service.UserService;
import edu.ncu.zww.imserver.service.tools.InvitationExchange;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Member;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static edu.ncu.zww.imserver.bean.TranObjectType.LOGIN;

/**
 *  服务器每连接一个client就创建一个这个，用来存放该socket
 *  使用者个协助socket管理输入输出流
 *
 */
public class SocketTask implements Runnable {

    /*  发送队列， 因为服务器有多个监听客户端的线程，当很多好友一起向他发送消息，每个服务器线程
	   都同时调用此实例的socket争夺send ，并发控制异常。*/
    private LinkedList<TranObject> sendQueue;
    private Server mServer; // 服务器，可使用其公共方法
    private Socket socket ; // 该客户端与服务器的socket，都是通过socket进行通讯
    private InputThread mInputListen; // 该客户端与服务器的读（入）线程监听
    private OutputThread mOutputListen;  // 该客户端与服务器的写（出）线程监听

    private ObjectOutputStream mOutput; // 该socket的对象输出流,实例需要有socket
    private ObjectInputStream mInput; // 该socket的对象输入流,实例需要有socket

    private UserService userService;
    private GroupService groupService;
    private User user;

    public SocketTask(Server server, Socket socket) {

        sendQueue = new LinkedList<TranObject>();
        this.mServer =server;
        this.socket = socket;
        userService = ApplicationContextUtil.getBean(UserService.class);
        groupService = ApplicationContextUtil.getBean(GroupService.class);
        user = new User();

        try {
            mOutput = new ObjectOutputStream(socket.getOutputStream()); // 获取socket对象输出流
            mInput = new MyObjectInputStream(socket.getInputStream());// 获取socket对象输入流
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
//        out = new OutputThread(socket, map);// 先实例化写消息线程,（把对应用户的写线程存入map缓存器中）
//        in = new InputThread(socket, out, map);// 再实例化读消息线程
//        out.setStart(true);
//        in.setStart(true);
//        in.start();
//        out.start();

        mInputListen = new InputThread(mInput, this);
        mOutputListen = new OutputThread(this);
        Thread listen = new Thread(mInputListen);
        Thread send = new Thread(mOutputListen);
        listen.start();
        send.start();
        System.out.println("Server、该socket读写线程已准备好");
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public User getUser() { // 该客户端的用户信息
        return user;
    }

    public void setUser(User user) {
        this.user.setAccount(user.getAccount());
        this.user.setName(user.getName());
        this.user.setAvatar(user.getAvatar());
        this.user.setSex(user.getSex());
    }

    /* ----------------    输入进程调用的方法处理     ---------------- */

    // 注册
    public void register(TranObject tranObj) {
        User registerUser = (User) tranObj.getObject();
        int rAccount = userService.register(registerUser);
        System.out.println(MyDate.getDateCN() + " 新用户注册:"
                + rAccount);

        // 给用户回复消息
        TranObject<User> t = new TranObject<User>(TranObjectType.REGISTER);
        User userData = new User();
        userData.setAccount(rAccount);
        t.onSuccess(userData);
        t.setInfo(String.valueOf(rAccount)); // 注册成功info填入账号
        send(t);
        System.out.println("注册返回数据"+t);
    }

    // 登录
    public void login(TranObject tranObj) {
        User loginUser = (User) tranObj.getObject();
        System.out.println("请求登录的用户"+loginUser);

        /** 准备处理结果 **/
        TranObject<ArrayList<User>> t = new TranObject<ArrayList<User>>(LOGIN);
        // 登录操作，存在用户则返回个人和好友列表信息,否则无数据
        ArrayList<User> users = userService.login(loginUser);
        if (users.size()> 0) {  // 如果登录成功

            Integer account = loginUser.getAccount(); // 获取账号
            userService.setUserOnline(account); // 数据库更新在线状态
            setUser(users.get(0));
            System.out.println(MyDate.getDateCN() + " 用户：" + loginUser.getAccount() + " 上线了");
            mServer.addClient(this.user.getAccount(),this); // 加入map映射
            System.out.println("当前在线人数：" + mServer.size());
            // 2.先广播在线用户这人已上线
//                                TranObject<User> onObject = new TranObject<User>(TranObjectType.LOGIN);
//                                User lu = new User();
//                                lu.setId(id);
//                                onObject.setObject(lu); // 返回只有该用户id信息的消息
//                                for (OutputThread onOut : map.getAll()) {
//                                    onOut.setMessage(onObject);// 获取map所有用户的写线程，广播一下用户上线
//                                }
//                                map.add(id, out);// 先广播，再把对应用户id的写线程存入map中，以便转发消息时调用
            // 3.返回登录数据
            t.onSuccess(users);// 把列表加入回复的对象中

        } else {
            t.onError("登录失败");
        }

        // 返回登录数据
        send(t);
    }

    /**
     * 关闭与客户端的连接
     */
    public void close() {
        try {
            socket.close(); // socket关闭后，他所在的流也都自动关闭
            mInputListen.close(); // 关闭读监听线程
            mOutputListen.close(); // // 关闭写监听线程
            if (user.getAccount() != 0)
                getOffLine();
            System.out.println(user.getAccount() + "下线了...");
        } catch (IOException e) {
            System.out.println("关闭失败.....");
            e.printStackTrace();
        }
    }

    /**
     * 客户端下线
     */
    public void getOffLine() {
        mServer.closeClientByID(user.getAccount());
    }

    // 添加好友
    public void friendInvitation(TranObject tranObj) {
        Invitation invitation = (Invitation) tranObj.getObject();
        String info = user.getName()+"请求添加好友";
        // 如果是同意状态
        if (StatusText.CONTACT_AGREE.equals(invitation.getStatus())) {
            Integer userAccount = tranObj.getFromUser();
            Integer contactAccount = tranObj.getToUser();
            // 数据库互相添加好友信息
            userService.makeContact(userAccount,contactAccount);

            // 返回添加成功信息给自己,返回原数据即可
            TranObject newTran1 = new TranObject(tranObj.getType());
            newTran1.onSuccess(invitation);
            newTran1.setInfo("成功添加好友"+invitation.getName());
            System.out.println("---------点击同意好友返回给自己的数据--------");
            System.out.println(newTran1);
            System.out.println("-------------------------------------------");

            send(newTran1);

            // 将要转发给好友的tranObj的info更改
            tranObj.setInfo(user.getName()+"同意了你的好友请求");
        } else {
            tranObj.setInfo(user.getName()+"请求添加好友");
            invitation.setStatus(StatusText.CONTACT_SELECT);
        }
        // 向好友发送该邀请信息,数据和原数据的差别在于invitation身份调换
        invitation.setAccount(user.getAccount());
        invitation.setName(user.getName());
        invitation.setAvatar(user.getAvatar());
//        tranObj.onSuccess(invitation); //  双方身份信息调换
        System.out.println("------------------发给好友的邀请信息--------------");
        System.out.println(tranObj);
        System.out.println("------------------------------------------------");
        sendFriend(tranObj);
    }

    // 消息
//    public void login(TranObject tranObj) {
//
//    }

    // 添加群
    public void groupInvitation(TranObject tranObj) {

    }

    // 创建群
    public void createGroup(TranObject tranObj) {
        GroupInfo groupInfo = (GroupInfo) tranObj.getObject();
        groupInfo.setCreateTime(new Date());
        // 保存群基本信息和群成员
        groupService.createGroup(user.getAccount(),groupInfo);
        // 将群加入到成员的Contact表中
        for (Integer account: groupInfo.getMemberList()) {
            userService.addGroup(account,groupInfo.getGid());
        }
        tranObj.setStatus(0);// 成功
        send(tranObj);
    }

    public void cdGroupMember(TranObject tranObj) {
        GroupInfo groupInfo = (GroupInfo) tranObj.getObject();
        Integer gid = groupInfo.getGid();
        List<Integer> memberList = groupInfo.getMemberList();
        if (tranObj.getStatus()==0) { // 删除
            groupService.removeMember(gid,memberList); // 从群成员移除成员
            for (Integer account : memberList) { // 从成员移除群
                userService.removeGroup(account,gid);
            }
        } else { // 增加
            groupService.addMember(gid,memberList);
            for (Integer account : memberList) { // 从成员移除群
                userService.addGroup(account,gid);
            }
        }
    }

    // 获取用户群聊
    public void getGroupList(TranObject tranObj) {
        List<GroupInfo> groupList = userService.getGroupList(user.getAccount());
        tranObj.setStatus(0);// 成功
        tranObj.setObject(groupList);
        send(tranObj);
    }

    // 群成员
    public void getMember(TranObject tranObj) {
        int groupId = (int) tranObj.getObject();
        List<Contact> memberList = groupService.getMember(groupId);
        tranObj.setFromUser(groupId);
        tranObj.onSuccess(memberList);
        send(tranObj);
    }


    // 处理接收到的消息
    public void dealMessage(TranObject tranObj) {
        Message message = (Message) tranObj.getObject();
        if (message.getChatType()-0 == 0) { // 单人聊天
            Contact user = new Contact();
            user.setAccount(111);
            user.setName("erfde");
            message.setUser(user);
            sendFriend(tranObj);
        } else { // 群聊天
            Integer groupId = tranObj.getToUser();
            List<Integer> memberList = groupService.getMemberId(groupId);
            for (Integer account : memberList) {
                TranObject newTranObj = new TranObject<Message>(tranObj.getType());
                newTranObj.setToUser(account); // 用于在线直接得到用户账号
                message.setReceiveId(account); // 用于保存数据时存放用户账号
                newTranObj.setObject(message);
                sendFriend(tranObj);
            }
        }
        // 返回数据
        // send(tranObj)
    }



    /**--------------- 转发消息给好友 ------------------**/
    private void sendFriend(TranObject tranObject) {
        int friendAccount = tranObject.getToUser();
        // 如果接收者已连接服务器
        if ( mServer.isContatinId(friendAccount) ) {
            mServer.getClientByID(friendAccount).insertQueue(tranObject);
        } else {
            // 存到数据库

        }
    }

    /**--------------- 发送消息 ------------------**/

    // 直接使用输出流发送消息，
    public synchronized void send(TranObject tran) {
        try {
            System.out.println("-------------------- 向客户端发送数据-----------------------");
            System.out.println(tran);
            System.out.println("----------------------------------------------------------");
            mOutput.writeObject(tran);
            mOutput.flush();
            notify();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /******************************** 对发送队列的异步处理 ***********************************/
    /**
     * 发送数据 如果是从好友那里发送来的 就先添加到队列 并发控制，因为同步性太强 否则直接发送； 属于发送线程
     */
    public synchronized void insertQueue(TranObject tran) {
        sendQueue.add(tran);
    }

    public synchronized int sizeOfQueue() {
        return sendQueue.size();
    }

    public synchronized TranObject removeQueueEle(int i) {
        return sendQueue.remove(i);
    }
}
