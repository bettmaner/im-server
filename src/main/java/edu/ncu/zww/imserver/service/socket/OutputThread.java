package edu.ncu.zww.imserver.service.socket;

import edu.ncu.zww.imserver.bean.TranObject;
import edu.ncu.zww.imserver.bean.TranObjectType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class OutputThread extends Thread{
    private OutputThreadMap map;
    private ObjectOutputStream oos;
    private TranObject msg;
    private boolean isStart = true;// 循环标志位
    private Socket socket;

    public OutputThread(Socket socket, OutputThreadMap map) {
        try {
            this.socket = socket;
            this.map = map;
            oos = new ObjectOutputStream(socket.getOutputStream());// 在构造器里面实例化对象输出流
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setStart(boolean isStart) {
        this.isStart = isStart;
    }

    // 调用写消息线程，设置了消息之后，唤醒run方法，可以节约资源
    public void setMessage(TranObject msg) {
        this.msg = msg;
        synchronized (this) {
            notify();
        }
    }

    @Override
    public void run() {
        try {
            while (isStart) {
                // 没有消息写出的时候，线程等待
                synchronized (this) {
                    wait();
                }
                if (msg != null) {
                    oos.writeObject(msg);
                    oos.flush();
                    System.out.println("传出去的对象"+msg+msg.getType().getClass());
                }
                if (msg.getType() == TranObjectType.LOGOUT) {// 如果是发送下线的消息，就直接跳出循环
                    break;
                }
            }
            if (oos != null)// 循环结束后，关闭流，释放资源
                oos.close();
            if (socket != null)
                socket.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
