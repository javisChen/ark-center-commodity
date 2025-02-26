package com.ark.center.member.client.member.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(
    enumAsRef = true, 
    description = """
        积分计算类型:
         * `FIXED` - 固定值
         * `RATE` - 比例值
        """
)
public enum PointsCalcType {
    
    FIXED("固定值"),
    RATE("比例值");
    
    private final String description;
    
    PointsCalcType(String description) {
        this.description = description;
    }
} 