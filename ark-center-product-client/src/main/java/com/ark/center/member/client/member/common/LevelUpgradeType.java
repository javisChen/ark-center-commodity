package com.ark.center.member.client.member.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(
    enumAsRef = true, 
    description = """
        等级升级类型:
         * `INIT` - 初始化
         * `GROWTH_VALUE` - 成长值升级
         * `CONSUME_AMOUNT` - 消费金额升级
         * `ORDER_COUNT` - 订单数量升级
         * `MANUAL` - 人工调整
        """
)
public enum LevelUpgradeType {
    
    INIT("初始化"),
    GROWTH_VALUE("成长值升级"),
    CONSUME_AMOUNT("消费金额升级"),
    ORDER_COUNT("订单数量升级"),
    MANUAL("人工调整");
    
    private final String description;
    
    LevelUpgradeType(String description) {
        this.description = description;
    }
} 