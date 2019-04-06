package edu.ncu.zww.imserver.controller;

import edu.ncu.zww.imserver.common.util.ApplicationContextUtil;
import edu.ncu.zww.imserver.common.util.Constants;
import edu.ncu.zww.imserver.common.util.MyDate;
import edu.ncu.zww.imserver.service.UserService;
import edu.ncu.zww.imserver.service.socket.InputThread;
import edu.ncu.zww.imserver.service.socket.OutputThread;
import edu.ncu.zww.imserver.service.socket.OutputThreadMap;
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
    private boolean isStarted = true;
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
            quit();
        } /*catch (MessagingException e) {
            e.printStackTrace();
        }*/
    }

    public void start() {
        try {
            while (isStarted) {
                System.out.println(MyDate.getDateCN() + " 服务器已启动...");
                socket = serverSocket.accept();
                System.out.println("客户端已经连接");
                String ip = socket.getInetAddress().toString();
                System.out.println(MyDate.getDateCN() + " 用户：" + ip + " 已建立连接");
                // 为支持多用户并发访问，采用线程池管理每一个用户的连接请求
                if (socket.isConnected())
                    executorService.execute(new SocketTask(socket));// 添加到线程池
            }
            if (socket != null)
                socket.close();
            if (serverSocket != null)
                serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
            // isStarted = false;
        }
    }

    private final class SocketTask implements Runnable {
        private Socket socket = null;
        private InputThread in;
        private OutputThread out;
        private OutputThreadMap map;

        public SocketTask(Socket socket) {
            this.socket = socket;
            map = OutputThreadMap.getInstance();
        }

        @Override
        public void run() {
            out = new OutputThread(socket, map);//
            // 先实例化写消息线程,（把对应用户的写线程存入map缓存器中）
            in = new InputThread(socket, out, map);// 再实例化读消息线程
            out.setStart(true);
            in.setStart(true);
            in.start();
            out.start();
            System.out.println("Server、该socket读写线程已准备好");
        }
    }

    /**
     * 退出
     */
    public void quit() {
        try {
            this.isStarted = false;
            // 是否还要读写线程strat为false
            serverSocket.close();
            System.out.println("此serverSocket已销毁");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*public static void main(String[] args) {
        new Server().start();
    }*/
}
