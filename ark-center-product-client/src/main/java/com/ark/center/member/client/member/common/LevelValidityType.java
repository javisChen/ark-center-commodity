package com.ark.center.member.client.member.common;

import lombok.Getter;

@Getter
public enum LevelValidityType {
    
    PERMANENT("永久有效"),
    FIXED("固定期限"),
    PERIODIC("周期性");
    
    private final String desc;
    
    LevelValidityType(String desc) {
        this.desc = desc;
    }
} 