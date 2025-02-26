package com.ark.center.member.client.member.common;

import lombok.Getter;

@Getter
public enum PointsRecordType {
    
    EARN("获取"),
    CONSUME("消费"),
    EXPIRE("过期"),
    FREEZE("冻结"),
    UNFREEZE("解冻");
    
    private final String desc;
    
    PointsRecordType(String desc) {
        this.desc = desc;
    }
} 