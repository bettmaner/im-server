package edu.ncu.zww.imserver.bean;

import java.io.Serializable;
import java.util.Date;

public class Invitation implements Serializable {

    private static final long serialVersionUID = 1L;

    private String uuid;

    private int account; // 接受者账号

    private String name; // 接受者姓名

    private String avatar; // 接受者图像

    private String info;

    private Date createDate; // 创建时间

    private int type; // 邀请类型，0人1群

    private String status; // 邀请状态

    public void setToUser(Contact toUser) {
        this.account = toUser.getAccount();
        this.name = toUser.getName();
        this.avatar = toUser.getAvatar();
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public int getAccount() {
        return account;
    }

    public void setAccount(int account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Invitation{" +
                "uuid='" + uuid + '\'' +
                ", account=" + account +
                ", name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", info='" + info + '\'' +
                ", createDate=" + createDate +
                ", type=" + type +
                ", status='" + status + '\'' +
                '}';
    }
}
