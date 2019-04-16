package edu.ncu.zww.imserver.mapper;

import edu.ncu.zww.imserver.bean.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface UserMapper {

    public Integer getAccount(String email);

    public User getUserByAccount(int account);

    public Integer login( User user);

    public void setUserOnline(Integer account);

    public void insertUser(User user);

    public List queryUser(String account); // 模糊查询其他用户

    /*@Update({"create table ${tablename} ( taskid int PRIMARY KEY NOT NULL AUTO_INCREMENT," +
            " title varchar(20) NOT NULL," +
            " detail varchar(200) DEFAULT NULL," +
            " status int(1) DEFAULT 0," +
            " priority int(1) DEFAULT 0," +
            " exceptfinishdate date DEFAULT NULL," +
            " createddate date DEFAULT NULL)" +
            " ENGINE=InnoDB DEFAULT CHARSET=utf8"})
    public void createTableByUserId(@Param("tablename") String tablename);*/

    // 创建存放好友的表
    public void createTableByUserAccount(@Param("tableName") Integer tableName);

    public ArrayList<User> getFriends(@Param("account") int account);
}
