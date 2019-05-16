package edu.ncu.zww.imserver.bean;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private int account;  // 登录账号。唯一标识

    private String name;    // 昵称。不为空，默认unknown

    private String email;   // qq邮箱

    private String password;

    private String avatar;    // 头像

    private int sex;    // 0 男性，1 女性

    private int isOnline; // 0 离线, 1 在线

    private String ip;

    private int port;

    private int groups;  // 哪一个分组


    public int getAccount() {
        return account;
    }

    public void setAccount(int account) {
        this.account = account;
    }

    public int getGroups() {
        return groups;
    }

    public void setGroups(int groups) {
        this.groups = groups;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(int isOnline) {
        this.isOnline = isOnline;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof User) {
            User user = (User) o;
            if (user.getAccount() == account && user.getIp().equals(ip)
                    && user.getPort() == port) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "User [account=" + account + ", name=" + name + ", email=" + email
                + ", password=" + password + ", isOnline=" + isOnline
                + ", avatar=" + avatar + ", sex=" + sex + ", ip=" + ip + ", port=" + port + ", groups="
                + groups + "]";
    }
}
