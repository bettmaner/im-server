package edu.ncu.zww.imserver.controller;

import edu.ncu.zww.imserver.bean.User;
import edu.ncu.zww.imserver.common.util.EmailType;
import edu.ncu.zww.imserver.mapper.UserMapper;
import edu.ncu.zww.imserver.service.UserService;
import edu.ncu.zww.imserver.service.tools.MailService;
import edu.ncu.zww.imserver.service.tools.MailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.util.ArrayList;

@RestController
public class Test {
    @Autowired
    private MailServiceImpl mailService;

    @Autowired
    UserService userService;
    @Autowired
    UserMapper userMapper;
    @GetMapping("/sign")
    public String game() throws MessagingException {
        //System.out.println("可以访问"+userService.getId("1987508059@qq.com"));

        //mailService.sendMail("1756919428@qq.com",EmailType.REGISTER);
        //userService.createTableByUserId(12111);
        /*Lis list = new ArrayList<User>();

        String id = "000";
        userMapper.setUserOnline(0);
        list = userService.queryUser(id);

        for (User a: list) {
            System.out.println(a);
        }*/
//            System.out.println(list.size());
        //System.out.println();

        return "加卡晋级赛";
    }
}
