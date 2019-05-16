package edu.ncu.zww.imserver.bean;

import java.io.Serializable;
import java.util.Date;

/**
 *  联系人
 *
 * */
public class Contact implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer account; // 账号。唯一标识

    private String name; // 名字。不为空.

    private String nick; // 备注

    private String avatar; // 图像地址

    private int isContact; // 是否为好友，0否1是

    private int sex;    // 0 男性，1 女性

    private Date data; // 时间

    public Integer getAccount() {
        return account;
    }

    public void setAccount(Integer account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getIsContact() {
        return isContact;
    }

    public void setIsContact(int isContact) {
        this.isContact = isContact;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "account=" + account +
                ", name='" + name + '\'' +
                ", nick='" + nick + '\'' +
                ", avatar='" + avatar + '\'' +
                ", isContact=" + isContact +
                ", sex=" + sex +
                ", data=" + data +
                '}';
    }
}
