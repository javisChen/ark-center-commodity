package com.ark.center.member.client.member.common;

import lombok.Getter;

@Getter
public enum MemberStatus {
    
    ENABLE("正常"),
    DISABLE("禁用");
    
    private final String desc;
    
    MemberStatus(String desc) {
        this.desc = desc;
    }
}