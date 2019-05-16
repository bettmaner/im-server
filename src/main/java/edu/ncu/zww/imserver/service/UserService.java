package edu.ncu.zww.imserver.service;

import edu.ncu.zww.imserver.bean.User;
import edu.ncu.zww.imserver.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service("userService")
public class UserService {
    @Autowired
    UserMapper userMapper;


//    public Integer getId(String email){
//        return userMapper.getId(email);
//    }

    public void insertUser(User user) {
        userMapper.insertUser(user);
    }

    public Integer register(User user){
        Integer account = userMapper.getAccount(user.getEmail());
        System.out.println(account);
        if (account == 0) {
            userMapper.insertUser(user); // 1.添加用户信息至user表
            account = userMapper.getAccount(user.getEmail()); // 获取该用户的account
            System.out.println("新用户注册文件"+account);
            userMapper.createTableByUserAccount(account); // 以account创建表名，用来存放好友信息
        }
        return account;
    }

    public ArrayList<User> login(User user){
//        Integer account =  userMapper.login(user.getId(),user.getPassword());
        ArrayList<User> list = new ArrayList<User>();
        Integer y =  userMapper.login(user);
        if (y > 0) {    // 存在该用户
            Integer account = user.getAccount();
            User userInfo = userMapper.getUserByAccount(account);
            list.add(userInfo);
            ArrayList<User> friends = userMapper.getFriends(account);
            list.addAll(friends);
            for (User a: list) {
                System.out.println(a);
            }
        }
        return list;
    }

    public void setUserOnline(Integer account){
       userMapper.setUserOnline(account);
    }

    public List queryUser(String account) {
        return userMapper.queryUser(account);
    }

    // 建立好友关系
    public void makeContact(Integer account1,Integer account2) {
        userMapper.insertFriend(account1,account2);
        userMapper.insertFriend(account2,account1);
    }

//    public void createTableByUserId(Integer id){
//        userMapper.createTableByUserId(id);
//    }
}
