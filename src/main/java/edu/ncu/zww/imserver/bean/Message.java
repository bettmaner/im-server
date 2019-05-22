package edu.ncu.zww.imserver.bean;

import edu.ncu.zww.imserver.common.util.Constants;

import java.io.Serializable;
import java.util.Date;

/**
 * 聊天的消息类
 * */
public class Message implements Serializable/*,
        MessageContentType.Image*//*, *//**//*this is for default image messages implementation*//**//*
        MessageContentType *//**//*and this one is for custom content type (in this case - voice message)*/ {

    private static final long serialVersionUID = 1L;
    private String id; // uuid
    private Integer chatType; // 聊天对象类型。
    private Integer groupId; // 群id；如果不是群聊天则不管
    private Date createdAt; // 创建时间
    private Contact user; // 信息发送方的信息
    private Integer receiveId; // 接收者账号
    private String sendStatus; // 发送状态
    private String msgType; // 消息类型MsgType,默认文本类型
    private String text; // 消息文本
    private ImgMsgBody image;
    private Object Annex; // 附件
//    private Voice voice;

    public Message(){}

    public Message(String id, Contact user, String text) {
        this(id, user, text, new Date());
    }

    public Message(String id, Contact user, String text, Date createdAt) {
        this.id = id;
        this.text = text;
        this.user = user;
        this.createdAt = createdAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Contact getUser() {
        return this.user;
    }

    public ImgMsgBody getImage() {
        return image;
    }

    public Integer getChatType() {
        return chatType;
    }

    public void setChatType(Integer chatType) {
        this.chatType = chatType;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public void setUser(Contact user) {
        this.user = user;
    }

    public Integer getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(Integer receiveId) {
        this.receiveId = receiveId;
    }

//    public Voice getVoice() {
//        return voice;
//    }

    public String getStatus() {
        return this.sendStatus;
    }

    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setImage(ImgMsgBody image) {
        this.image = image;
        setMsgType(Constants.MSG_TYPE_IMAGE);
    }

    public Object getAnnex() {
        return Annex;
    }

    public void setAnnex(Object annex) {
        Annex = annex;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", chatType=" + chatType +
                ", groupId=" + groupId +
                ", createdAt=" + createdAt +
                ", user=" + user +
                ", receiveId=" + receiveId +
                ", sendStatus='" + sendStatus + '\'' +
                ", msgType='" + msgType + '\'' +
                ", text='" + text + '\'' +
                ", image=" + image +
                ", Annex=" + Annex +
                '}';
    }
}
