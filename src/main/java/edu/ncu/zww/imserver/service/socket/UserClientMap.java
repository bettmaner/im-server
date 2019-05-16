package edu.ncu.zww.imserver.service.socket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
*  在服务器中保存各个在线用户的SocketTask（client），将用户account和自己的SocketTask对应。
*
*  @ params
*  id: 用户id
*  out: 写线程
*/
public class UserClientMap {
    private HashMap<Integer, SocketTask> clientMap;
    private static UserClientMap instance; // 此静态实例类加载完成后就一直存在

    // 私有构造器，防止被外面实例化改对象
    private UserClientMap() {
        clientMap = new HashMap<Integer, SocketTask>();
    }

    // 单例模式像外面提供该对象
    public synchronized static UserClientMap getInstance() {
        if (instance == null) {
            instance = new UserClientMap();
        }
        return instance;
    }

    /**   暴露方法  **/

    // 获取用户account的client
    public synchronized  SocketTask getClientById(int account){
        return clientMap.get(account);
    }

    // 添加该用户的client，登录成功后调用
    public synchronized void addClient(int id, SocketTask client){
        clientMap.put(id, client);
    }

    // 移除该用户的client，下线后调用
    public synchronized void removeClient(int id){
        clientMap.remove(id);
    }

    // 该用户是否连接上服务器
    public synchronized boolean isContainId(int id){
        return clientMap.containsKey(id);
    }

    // 已经连接上服务器的用户总数
    public int size() {
        return clientMap.size();
    }


//    // 添加写线程的方法
//    public synchronized void add(Integer id, OutputThread out) {
//        map.put(id, out);
//    }
//
//    // 移除写线程的方法
//    public synchronized void remove(Integer id) {
//        map.remove(id);
//    }
//
//    // 取出写线程的方法,群聊的话，可以遍历取出对应写线程
//    public synchronized OutputThread getById(Integer id) {
//        return map.get(id);
//    }

    // 得到所有写线程方法，用于向所有在线用户发送广播
//    public synchronized List<OutputThread> getAll() {
//        List<OutputThread> list = new ArrayList<OutputThread>();
//        for (Map.Entry<Integer, OutputThread> entry : map.entrySet()) {
//            list.add(entry.getValue());
//        }
//        return list;
//    }
}
