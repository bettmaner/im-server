package edu.ncu.zww.imserver.common.util;

import org.springframework.stereotype.Component;

public enum EmailType {
    REGISTER("注册账号"), // 注册邮件
    FORGET("重置密码"); // 重置密码邮件
    private String name; // 中文
    private EmailType(String name) {
        this.name = name;
    }
    public String getName(){
        return name;
    }
}
