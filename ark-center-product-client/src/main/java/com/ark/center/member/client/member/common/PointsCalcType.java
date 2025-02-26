package com.ark.center.member.client.member.common;

import lombok.Getter;

@Getter
public enum PointsCalcType {
    
    FIXED("固定值"),
    RATE("比例");
    
    private final String desc;
    
    PointsCalcType(String desc) {
        this.desc = desc;
    }
} 