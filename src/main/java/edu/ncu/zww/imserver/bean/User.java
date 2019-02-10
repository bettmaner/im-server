package edu.ncu.zww.imserver.bean;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;  // 登录账号
    private String name;    // 昵称
    private String email;   // qq邮箱
    private String password;
    private int img;    // 头像
    private int sex;    // 0 男性，1 女性
    private int isOnline; // 0 离线, 1 在线
    private String ip;
    private int port;
    private int group;  // 哪一个分组

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
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

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
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
            if (user.getId() == id && user.getIp().equals(ip)
                    && user.getPort() == port) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", email=" + email
                + ", password=" + password + ", isOnline=" + isOnline
                + ", img=" + img + ", sex=" + sex + ", ip=" + ip + ", port=" + port + ", group="
                + group + "]";
    }
}
