package com.ark.center.member.client.member.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(
    enumAsRef = true, 
    description = """
        积分记录类型:
         * `EARN` - 获取积分
         * `CONSUME` - 消费积分
         * `EXPIRE` - 积分过期
         * `FREEZE` - 积分冻结
         * `UNFREEZE` - 积分解冻
        """
)
public enum PointsRecordType {
    
    EARN("获取积分"),
    CONSUME("消费积分"),
    EXPIRE("积分过期"),
    FREEZE("积分冻结"),
    UNFREEZE("积分解冻");
    
    private final String description;
    
    PointsRecordType(String description) {
        this.description = description;
    }
} 