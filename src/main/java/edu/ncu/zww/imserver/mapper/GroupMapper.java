package edu.ncu.zww.imserver.mapper;

import edu.ncu.zww.imserver.bean.Contact;
import edu.ncu.zww.imserver.bean.GroupInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface GroupMapper {

    public int insertGroupInfo(GroupInfo groupInfo);

    public void addMember(@Param("gid") Integer gid,@Param("memberList") List<Integer> member);

    public void removeMember(@Param("gid") Integer gid,@Param("memberList") List<Integer> member);

    public void deleteMember(@Param("gid") Integer gid,@Param("memberList") Integer member);

    public List<Contact> getMember(Integer gid);

    public List<Integer> getMemberId(Integer gid);

}
