package com.ark.center.member.client.member.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(
    enumAsRef = true, 
    description = """
        积分规则类型:
         * `EARN` - 获取积分规则
         * `CONSUME` - 消费积分规则
        """
)
public enum PointsRuleType {
    
    EARN("获取积分规则"),
    CONSUME("消费积分规则");
    
    private final String description;
    
    PointsRuleType(String description) {
        this.description = description;
    }
} 