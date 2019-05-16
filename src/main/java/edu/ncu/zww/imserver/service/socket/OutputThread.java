package edu.ncu.zww.imserver.service.socket;

import edu.ncu.zww.imserver.bean.TranObject;
import edu.ncu.zww.imserver.bean.TranObjectType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class OutputThread implements Runnable {
    private SocketTask mClient;
    private boolean isStart = true;// 循环标志位


    public OutputThread(SocketTask client) {
        mClient = client;
    }

    @Override
    public void run() {
        while (isStart) {
            // 没有消息写出的时候，线程等待
            if (mClient.sizeOfQueue() == 0) {
                try {
                    // 若没有数据则阻塞
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                TranObject tran = mClient.removeQueueEle(0);
                mClient.send(tran);
            }
//                if (msg != null) {
//                    oos.writeObject(msg);
//                    oos.flush();
//                    System.out.println("传出去的对象"+msg+msg.getType().getClass());
//                }
        }
        if (mClient != null)
            mClient = null;
    }

    public void close() {
        isStart = false;
    }
}
