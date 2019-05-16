package edu.ncu.zww.imserver.bean;


import java.io.Serializable;

/**
 * 作为与客户端通讯的序列化对象，类似于json.
 */
public class TranObject<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer status; // 返回状态，0成功，1失败 , 2等待处理（2情况是需要他人确认）

    private String info; // 消息，主要为错误信息

    private String type; // 业务类型

    private int fromUser;// 来自哪个用户

    private int toUser;// 发往哪个用户

    private T object;// 传输的对象

    // 构造函数
    public TranObject(String dealType) {
        this.type = dealType;
    }

    public void onSuccess(T object){
        this.setStatus(0);
        this.setInfo("成功");
        this.setObject(object);
    }

    public void onError(String info){
        this.setStatus(1);
        this.setInfo(info);
        this.setObject(null);
    }

    //以下为Getter和Setter和toString

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getFromUser() {
        return fromUser;
    }

    public void setFromUser(int fromUser) {
        this.fromUser = fromUser;
    }

    public int getToUser() {
        return toUser;
    }

    public void setToUser(int toUser) {
        this.toUser = toUser;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "TranObject [type=" + type + ",status="+status+ ",info="+info
                +", fromUser=" + fromUser + ", toUser=" + toUser + ", object=" + object + "]";
    }
}
