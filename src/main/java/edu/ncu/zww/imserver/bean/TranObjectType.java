package edu.ncu.zww.imserver.bean;

/**
 *  封装传输对象的业务类型，在TranObject使用.
 **/
public class TranObjectType {
    public final static String REGISTER = "REGISTER";   // 注册
    public final static String LOGIN = "LOGIN"; // 用户登录
//    public final static String SEARCH_FRIEND = "SEARCH_FRIEND"; // 查找好友
//    public final static String SEARCH_GROUP = "SEARCH_GROUP"; // 查找群
    public final static String FRIEND_REQUEST = "FRIEND_REQUEST"; // 好友请求
    public final static String GROUP_REQUEST = "GROUP_REQUEST"; // 群请求
    public final static String CREATE_GROUP = "CREATE_GROUP"; // 创建群聊
    public final static String LOGOUT = "LOGOUT";   // 用户退出登录
    public final static String FRIENDLOGIN = "FRIENDLOGIN"; // 好友上线
    public final static String FRIENDLOGOUT = "FRIENDLOGOUT";   // 好友下线
    public final static String MESSAGE = "MESSAGE"; // 用户发送消息
    public final static String UNCONNECTED = "UNCONNECTED"; // 无法连接
    public final static String FILE = "FILE";// 传输文件
    public final static String REFRESH = "REFRESH"; // 刷新好友列表
}
