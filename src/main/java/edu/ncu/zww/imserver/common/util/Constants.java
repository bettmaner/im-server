package edu.ncu.zww.imserver.common.util;

public class Constants {
    //public static final String SERVER_IP = "192.168.1.100";
    public static final String SERVER_IP = "192.168.37.2";
    public static final int SERVER_PORT = 12345;

    public static final int CLIENT_SERVER_PORT = 8081;
    public static final int CLIENT_FILE_TRANSPORT_PORT = 8082;
    public static final int REGISTER_FAIL = 0;

    /* 序列化对象包名修改 */
    // 即客户端和服务端包名
    public static final String BEAN_OLD_PACKAGE = "edu.ncu.zww.app.wei_im.mvp.model.bean";
    public static final String BEAN_NEW_PACKAGE = "edu.ncu.zww.imserver.bean";

    // 消息属性
    public static final String MSG_TYPE_TEXT = "TEXT"; // 文本消息
    public static final String MSG_TYPE_AUDIO = "AUDIO"; // 语音消息
    public static final String MSG_TYPE_VIDEO = "VIDEO"; // 视频消息
    public static final String MSG_TYPE_IMAGE = "IMAGE"; // 图片消息
    public static final String MSG_TYPE_FILE = "FILE"; // 文件消息
    public static final String MSG_TYPE_LOCATION = "LOCATION"; // 位置消息
    public static final String DEFAULT = "DEFAULT";
    public static final String SENDING = "SENDING"; // 发送中
    public static final String FAILED = "FAILED"; // 发送失败
    public static final String SENT = "SENT"; // 已发送
}

