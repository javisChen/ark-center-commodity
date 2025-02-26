package com.ark.center.member.client.member.common;

import lombok.Getter;

@Getter
public enum PointsRuleType {
    
    EARN("获取积分"),
    CONSUME("消费积分");
    
    private final String desc;
    
    PointsRuleType(String desc) {
        this.desc = desc;
    }
} 