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
        if (typeStr==null) {
            return new ArrayList();
        }
        Integer type = Integer.valueOf(typeStr);
        if (type == 0) {
            return userService.queryUser(account);
        } else {
            //待完善，返回群
            return userService.queryUser(account);
        }
    }

}
