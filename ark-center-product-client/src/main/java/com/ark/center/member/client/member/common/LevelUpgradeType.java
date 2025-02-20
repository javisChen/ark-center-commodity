package com.ark.center.member.client.member.common;

import lombok.Getter;

@Getter
public enum LevelUpgradeType {
    
    INIT("初始化"),
    GROWTH_VALUE("成长值升级"),
    CONSUME_AMOUNT("消费金额升级"),
    ORDER_COUNT("订单数量升级"),
    MANUAL("人工调整");
    
    private final String desc;
    
    LevelUpgradeType(String desc) {
        this.desc = desc;
    }
} 