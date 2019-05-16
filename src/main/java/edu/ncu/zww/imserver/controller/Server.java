package edu.ncu.zww.imserver.controller;

import edu.ncu.zww.imserver.common.util.Constants;
import edu.ncu.zww.imserver.common.util.MyDate;
import edu.ncu.zww.imserver.service.socket.SocketTask;
import edu.ncu.zww.imserver.service.socket.UserClientMap;
import edu.ncu.zww.imserver.service.tools.MailServiceImpl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private ExecutorService executorService;// 线程池
    private ServerSocket serverSocket = null;
    private Socket socket = null;
//    private boolean isStarted = true;
    private MailServiceImpl mailService;

    public Server() {
        try {
            // 创建线程池，池中具有(cpu个数*50)条线程
            executorService = Executors.newFixedThreadPool(Runtime.getRuntime()
                    .availableProcessors() * 50);
            serverSocket = new ServerSocket(Constants.SERVER_PORT);
            String ip = InetAddress.getLocalHost().getHostAddress();
            System.out.println("服务器ip地址：" + ip);

            //mailService.sendMail("1756919428@qq.com",EmailType.REGISTER);
        } catch (IOException e) {
            e.printStackTrace();
        } /*catch (MessagingException e) {
            e.printStackTrace();
        }*/
    }

    /**
     * 开启服务器
     */
    public void start() {
        try {
            while (true) {
                System.out.println(MyDate.getDateCN() + " 服务器已启动...");
                socket = serverSocket.accept(); // 不断接收新的socket
                String ip = socket.getInetAddress().toString();
                System.out.println(MyDate.getDateCN() + " 用户：" + ip + " 已建立连接");
                // 为支持多用户并发访问，采用线程池管理每一个用户的连接请求
                if (socket.isConnected())
                    executorService.execute(new SocketTask(this, socket));// 将每个socket任务添加到线程池
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭服务器
     */
    public void quit() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*
     * 管理在线用户对应的client
     */
    public SocketTask getClientByID(int account){
        return UserClientMap.getInstance().getClientById(account);
    }
    public void closeClientByID(int account){
        UserClientMap.getInstance().removeClient(account);
    }
    public void addClient(int account, SocketTask client){
        UserClientMap.getInstance().addClient(account, client);
    }
    public boolean isContatinId(int account){
        return UserClientMap.getInstance().isContainId(account);
    }
    public int  size() {
        return UserClientMap.getInstance().size();
    }
}
