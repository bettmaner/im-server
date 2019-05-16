package edu.ncu.zww.imserver.bean;

import java.io.Serializable;
import java.util.List;

// 群信息
public class GroupInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer gid; // 账号。唯一标识

    private String name; // 名字。不为空，默认unknown

    private String avatar; // 群头像

    private String Creator; // 群主

    private List<Integer> memberList; // 群成员账号

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
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

    public String getCreator() {
        return Creator;
    }

    public void setCreator(String creator) {
        Creator = creator;
    }

    public List<Integer> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<Integer> memberList) {
        this.memberList = memberList;
    }

    @Override
    public String toString() {
        return "Group{" +
                "gid=" + gid +
                ", name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", Creator='" + Creator + '\'' +
                '}';
    }
}
