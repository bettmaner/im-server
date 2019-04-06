package edu.ncu.zww.imserver.service.socket;

import edu.ncu.zww.imserver.bean.TranObject;
import edu.ncu.zww.imserver.bean.TranObjectType;
import edu.ncu.zww.imserver.bean.User;
import edu.ncu.zww.imserver.common.util.ApplicationContextUtil;
import edu.ncu.zww.imserver.common.util.MyDate;
import edu.ncu.zww.imserver.common.util.MyObjectInputStream;
import edu.ncu.zww.imserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

public class InputThread extends Thread{
    private Socket socket;// socket对象
    private OutputThread out;// 传递进来的写消息线程，因为我们要给用户回复消息啊
    private OutputThreadMap map;// 写消息线程缓存器
    private ObjectInputStream ois;// 对象输入流
    private boolean isStart = true;// 是否循环读消息
    private UserService userService;

    public InputThread(Socket socket, OutputThread out, OutputThreadMap map) {
        this.socket = socket;
        this.out = out;
        this.map = map;
        try {
            ois = new MyObjectInputStream(socket.getInputStream());// 实例化对象输入流
        } catch (IOException e) {
            e.printStackTrace();
        }
        //userService = new UserService();
        userService = ApplicationContextUtil.getBean(UserService.class);
    }

    public void setStart(boolean isStart) {// 提供接口给外部关闭读消息线程
        this.isStart = isStart;
    }

    @Override
    public void run() {
        try {
            while (isStart) {
                // 读取消息
                readMessage();
            }
            if (ois != null)
                ois.close();
            if (socket != null)
                socket.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
            while ((readObject=ois.readObject()) != null) {
                if (readObject instanceof TranObject) {
                    TranObject read_tranObject = (TranObject) readObject;// 转换成传输对象
                    System.out.println(read_tranObject+" "+read_tranObject.getType().getClass());
                    switch (read_tranObject.getType()) {
                        case REGISTER:// 如果用户是注册
                            User registerUser = (User) read_tranObject.getObject();
//                    System.out.println(registerUser.toString());
                            int rAccount = userService.register(registerUser);

                            System.out.println(MyDate.getDateCN() + " 新用户注册:"
                                    + rAccount);
                            // 给用户回复消息
                            TranObject<User> rRObj = new TranObject<User>(TranObjectType.REGISTER);
                            User userData = new User();
                            userData.setAccount(rAccount);
                            rRObj.setObject(userData);
                            out.setMessage(rRObj);
                            System.out.println("注册返回数据"+rRObj);
                            break;
                        case LOGIN:
                            User loginUser = (User) read_tranObject.getObject();
                            System.out.println(loginUser);
                            TranObject<ArrayList<User>> lRObj = new TranObject<ArrayList<User>>(
                                    TranObjectType.LOGIN);
                            // 登录操作，存在用户则返回个人和好友列表信息
                            ArrayList<User> users = userService.login(loginUser);
                            if (lRObj != null) {// 如果登录成功,
                                /*
//                                1.先广播在线用户这人已上线
                                Integer id = loginUser.getId();
                                TranObject<User> onObject = new TranObject<User>(TranObjectType.LOGIN);
                                User lu = new User();
                                lu.setId(id);
                                onObject.setObject(lu); // 返回只有该用户id信息的消息
                                for (OutputThread onOut : map.getAll()) {
                                    onOut.setMessage(onObject);// 获取map所有用户的写线程，广播一下用户上线
                                }
                                map.add(id, out);// 先广播，再把对应用户id的写线程存入map中，以便转发消息时调用*/
                                // 2.返回登录数据
                                lRObj.setObject(users);// 把好友列表加入回复的对象中
                            } else {
                                lRObj.setObject(null);
                            }

                            // 返回登录数据
                            out.setMessage(lRObj);

                            System.out.println(MyDate.getDateCN() + " 用户："
                                    + loginUser.getAccount() + " 上线了");
                            System.out.println("用户信息为"+ loginUser.toString());
                            break;
                        case LOGOUT:// 如果是退出，更新数据库在线状态，同时群发告诉所有在线用户
                            User logoutUser = (User) read_tranObject.getObject();
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
                    /*int id2 = read_tranObject.getToUser();
                    OutputThread toOut = map.getById(id2);
                    if (toOut != null) {// 如果用户在线
                        toOut.setMessage(read_tranObject);
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
                    /*List<User> refreshList = dao.refresh(read_tranObject
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
