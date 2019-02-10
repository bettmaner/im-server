package edu.ncu.zww.imserver.controller;

import edu.ncu.zww.imserver.bean.User;
import edu.ncu.zww.imserver.common.util.EmailType;
import edu.ncu.zww.imserver.mapper.UserMapper;
import edu.ncu.zww.imserver.service.tools.MailService;
import edu.ncu.zww.imserver.service.tools.MailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
public class Test {
    @Autowired
    private MailServiceImpl mailService;
    @Autowired
    UserMapper userMapper;

    @GetMapping("/sign")
    public String game() throws MessagingException {
        System.out.println("可以访问"+EmailType.REGISTER.getName());

        //mailService.sendMail("1756919428@qq.com",EmailType.REGISTER);
        //User user = userMapper.getUserById(1);
        //userMapper.createTableByUserId("t123");
        userMapper.createTableByUserId(1211);
        return "加卡晋级赛";
    }
}
