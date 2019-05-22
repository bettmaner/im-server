package edu.ncu.zww.imserver.controller;

import edu.ncu.zww.imserver.bean.*;
import edu.ncu.zww.imserver.common.util.EmailType;
import edu.ncu.zww.imserver.mapper.GroupMapper;
import edu.ncu.zww.imserver.mapper.UserMapper;
import edu.ncu.zww.imserver.service.GroupService;
import edu.ncu.zww.imserver.service.UserService;
import edu.ncu.zww.imserver.service.tools.MailService;
import edu.ncu.zww.imserver.service.tools.MailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class Test {
    @Autowired
    private MailServiceImpl mailService;

    @Autowired
    UserService userService;
    @Autowired
    GroupService groupService;
    @Autowired
    UserMapper userMapper;
    @Autowired
    GroupMapper groupMapper;

    @GetMapping("/sign")
    public String game() throws MessagingException {

        List<Integer> memberList = groupService.getMemberId(6000011);

        return "加卡晋级赛"+memberList.size();
    }
}
