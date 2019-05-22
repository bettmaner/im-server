package edu.ncu.zww.imserver.service;

import edu.ncu.zww.imserver.bean.Contact;
import edu.ncu.zww.imserver.bean.GroupInfo;
import edu.ncu.zww.imserver.mapper.GroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("groupService")
public class GroupService {

    @Autowired
    GroupMapper groupMapper;

    public int createGroup(Integer creator,GroupInfo group) {
        groupMapper.insertGroupInfo(group); // 插入群基本信息,并将主键赋值给group的
        int gid = group.getGid();
        List<Integer> memberList = group.getMemberList();
        groupMapper.addMember(gid,memberList);
        return group.getGid();
    }

    public List<Contact> getMember(Integer gid) {
        return groupMapper.getMember(gid);
    }

    public List<Integer> getMemberId(Integer gid) {
        return groupMapper.getMemberId(gid);
    }

    public void addMember(Integer gid, List<Integer> memberList) {
        groupMapper.addMember(gid,memberList);
    }

    public void removeMember(Integer gid, List<Integer> memberList) {
        groupMapper.removeMember(gid,memberList);
    }
}
