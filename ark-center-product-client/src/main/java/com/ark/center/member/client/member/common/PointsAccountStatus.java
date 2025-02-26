package com.ark.center.member.client.member.common;

import lombok.Getter;

@Getter
public enum PointsAccountStatus {
    
    NORMAL("正常"),
    FROZEN("冻结");
    
    private final String desc;
    
    PointsAccountStatus(String desc) {
        this.desc = desc;
    }
} 