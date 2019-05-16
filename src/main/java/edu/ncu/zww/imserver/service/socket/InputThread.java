package edu.ncu.zww.imserver.service.socket;

import edu.ncu.zww.imserver.bean.TranObject;
import edu.ncu.zww.imserver.bean.TranObjectType;
import edu.ncu.zww.imserver.bean.User;
import edu.ncu.zww.imserver.common.util.ApplicationContextUtil;
import edu.ncu.zww.imserver.common.util.MyDate;
import edu.ncu.zww.imserver.common.util.MyObjectInputStream;
import edu.ncu.zww.imserver.service.UserService;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;

import static edu.ncu.zww.imserver.bean.TranObjectType.*;
/**
 *  读线程，每个用户连接上服务器都有一个读线程，并开启该线程一直读取
 * */
public class InputThread implements Runnable{
    private SocketTask mClient; // 该对象有socket和能操作socket输入输出
    private ObjectInputStream clientInput;// 对象输入流
    private boolean isStart;  // 是否循环读消息
    private UserService userService;

    public InputThread(ObjectInputStream clientInput, SocketTask client) {
        this.clientInput = clientInput;
        this.mClient = client;
        isStart = true;

        userService = ApplicationContextUtil.getBean(UserService.class);
    }

    public void setStart(boolean isStart) {// 提供接口给外部关闭读消息线程
        this.isStart = isStart;
    }

    @Override
    public void run() {
        SocketAddress s = mClient.getSocket().getRemoteSocketAddress();
        try {
            while (isStart) {
                // 读取消息
                readMessage();
            }
            if (clientInput != null)
                clientInput = null; // 将这里的this.clientInput指为空，clientInput的close由SocketTask负责
            if (clientInput != null)
                clientInput = null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void close() {
        isStart = false;
    }

    /**
     * 读消息以及处理消息，抛出异常
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void readMessage() throws IOException, ClassNotFoundException {
//        Object readObject = ois.readObject();
        Object readObject = null;
        try {
            while ((readObject = clientInput.readObject()) != null) {
                if (readObject instanceof TranObject) {
                    TranObject tranObj = (TranObject) readObject;// 转换成传输对象
                    System.out.println(tranObj+" "+tranObj.getType().getClass());
                    switch (tranObj.getType()) {
                        case REGISTER:// 如果用户是注册
//                            User registerUser = (User) read_tranObject.getObject();
//                            int rAccount = userService.register(registerUser);
//                            System.out.println(MyDate.getDateCN() + " 新用户注册:"
//                                    + rAccount);
//                            // 给用户回复消息
//                            TranObject<User> rRObj = new TranObject<User>(TranObjectType.REGISTER);
//                            User userData = new User();
//                            userData.setAccount(rAccount);
//                            rRObj.onSuccess(userData);
//                            rRObj.setInfo(String.valueOf(rAccount)); // 注册成功info填入账号
//                            out.setMessage(rRObj);
//                            System.out.println("注册返回数据"+rRObj);
                            mClient.register(tranObj);
                            break;

                        case LOGIN:
//                            User loginUser = (User) tranObj.getObject();
//                            System.out.println("请求登录的用户"+loginUser);
//                            TranObject<ArrayList<User>> lRObj = new TranObject<ArrayList<User>>(LOGIN);
//                            // 登录操作，存在用户则返回个人和好友列表信息,否则无数据
//                            ArrayList<User> users = userService.login(loginUser);
//                            if (users.size()> 0) {  // 如果登录成功,
//                                // 1.数据库更新在线状态
//                                Integer account = loginUser.getAccount(); // 获取账号
//                                userService.setUserOnline(account);
//
//                                // 2.先广播在线用户这人已上线
////                                TranObject<User> onObject = new TranObject<User>(TranObjectType.LOGIN);
////                                User lu = new User();
////                                lu.setId(id);
////                                onObject.setObject(lu); // 返回只有该用户id信息的消息
////                                for (OutputThread onOut : map.getAll()) {
////                                    onOut.setMessage(onObject);// 获取map所有用户的写线程，广播一下用户上线
////                                }
////                                map.add(id, out);// 先广播，再把对应用户id的写线程存入map中，以便转发消息时调用
//                                // 3.返回登录数据
//                                lRObj.onSuccess(users);// 把好友列表加入回复的对象中
//                                System.out.println(MyDate.getDateCN() + " 用户："
//                                        + loginUser.getAccount() + " 上线了");
//                            } else {
//                                lRObj.onError("登录失败");
//                            }
//
//                            // 返回登录数据
//                            out.setMessage(lRObj);
//                            System.out.println(MyDate.getDateCN() + " 用户："
//                                    + loginUser.getAccount() + " 上线了");
                            mClient.login(tranObj);
                            break;

                        case FRIEND_REQUEST:// 好友请求

                            // 0表示好友请求成功，1失败，2等待请求
//                            if ( 0 == tranObj.getStatus()) {
//                                Integer userAccount = tranObj.getFromUser();
//                                Integer friendAccount = tranObj.getToUser();
//                                // 两者数据库好友表互加信息
//                                // 向好友方发送自己的信息
//                                // 向自己发送添加成功信息
//                                User fromUser = (User) tranObj.getObject(); // 获取发送方信息
//
//                            }
                            // 转发邀请给联系人
                            // out.setMessage...
//                            UserService.
                            mClient.friendInvitation(tranObj);
                            break;
                        case GROUP_REQUEST:// 群请求
                            mClient.groupInvitation(tranObj);
                            break;
                        case LOGOUT:// 如果是退出，更新数据库在线状态，同时群发告诉所有在线用户
                            User logoutUser = (User) tranObj.getObject();
                    /*int offId = logoutUser.getId();
                    System.out
                            .println(MyDate.getDateCN() + " 用户：" + offId + " 下线了");
                    dao.logout(offId);
                    isStart = false;// 结束自己的读循环
                    map.remove(offId);// 从缓存的线程中移除
                    out.setMessage(null);// 先要设置一个空消息去唤醒写线程
                    out.setStart(false);// 再结束写线程循环

                    TranObject<User> offObject = new TranObject<User>(
                            TranObjectType.LOGOUT);
                    User logout2User = new User();
                    logout2User.setId(logoutUser.getId());
                    offObject.setObject(logout2User);
                    for (OutputThread offOut : map.getAll()) {// 广播用户下线消息
                        offOut.setMessage(offObject);
                    }*/
                            break;
                        case MESSAGE:// 如果是转发消息（可添加群发）
                            // 获取消息中要转发的对象id，然后获取缓存的该对象的写线程
                    /*int id2 = tranObj.getToUser();
                    OutputThread toOut = map.getById(id2);
                    if (toOut != null) {// 如果用户在线
                        toOut.setMessage(tranObj);
                    } else {// 如果为空，说明用户已经下线,回复用户
                        TextMessage text = new TextMessage();
                        text.setMessage("亲！对方不在线哦，您的消息将暂时保存在服务器");
                        TranObject<TextMessage> offText = new TranObject<TextMessage>(
                                TranObjectType.MESSAGE);
                        offText.setObject(text);
                        offText.setFromUser(0);
                        out.setMessage(offText);
                    }*/
                            break;
                        case REFRESH:
                    /*List<User> refreshList = dao.refresh(tranObj
                            .getFromUser());
                    TranObject<List<User>> refreshO = new TranObject<List<User>>(
                            TranObjectType.REFRESH);
                    refreshO.setObject(refreshList);
                    out.setMessage(refreshO);*/
                            break;
                        default:
                            break;
                    }
                }
            }
        } catch (EOFException e) {
            e.getStackTrace();
        }
    }



}
