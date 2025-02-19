package com.ark.center.member.client.member.common;

import lombok.Getter;

@Getter
public enum RegisterType {
    MOBILE(1, "手机号注册"),
    USERNAME(2, "用户名注册");
    
    private final int code;
    private final String desc;
    
    RegisterType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
} 