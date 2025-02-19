package com.ark.center.member.client.member.common;

import lombok.Getter;

@Getter
public enum Gender {
    
    UNKNOWN("未知"),
    MALE("男"),
    FEMALE("女");
    
    private final String desc;
    
    Gender(String desc) {
        this.desc = desc;
    }
} 