package edu.ncu.zww.imserver.controller;

import edu.ncu.zww.imserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
public class NetControl {
    @Autowired
    UserService userService;

    @RequestMapping("/queryContact")
    public List queryContact(HttpServletRequest request) {
        String account = request.getParameter("account");
        String typeStr = request.getParameter("type");
        System.out.println("找联系人"+account);
        if (typeStr==null) {
            return new ArrayList();
        }
        Integer type = Integer.valueOf(typeStr);
        if (type == 0) {
            List list = userService.queryUser(account);
            System.out.println("----------输出查询结果--------");
            for (int i=0;i<list.size();i++) {
                System.out.println(list.toString());
            }
            return list;
        } else {
            //待完善，返回群
            return userService.queryUser(account);
        }
    }

}
