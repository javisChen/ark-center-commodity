package com.ark.center.member.client.member.common;

import lombok.Getter;

@Getter
public enum IdentityType {
    
    MOBILE("手机号"),
    EMAIL("邮箱"),
    USERNAME("用户名"),
    WECHAT("微信"),
    QQ("QQ");
    
    private final String desc;
    
    IdentityType(String desc) {
        this.desc = desc;
    }
} 