package edu.ncu.zww.imserver.mapper;

import edu.ncu.zww.imserver.bean.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    public User getUserById(int id);

    public void insertUser(User user);
    /*@Update({"create table ${tablename} ( taskid int PRIMARY KEY NOT NULL AUTO_INCREMENT," +
            " title varchar(20) NOT NULL," +
            " detail varchar(200) DEFAULT NULL," +
            " status int(1) DEFAULT 0," +
            " priority int(1) DEFAULT 0," +
            " exceptfinishdate date DEFAULT NULL," +
            " createddate date DEFAULT NULL)" +
            " ENGINE=InnoDB DEFAULT CHARSET=utf8"})
    public void createTableByUserId(@Param("tablename") String tablename);*/
    public void createTableByUserId(@Param("tableName") Integer tableName);
}