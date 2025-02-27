package com.ark.center.member.client.member.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(
    enumAsRef = true, 
    description = """
        等级有效期类型:
         * `PERMANENT` - 永久有效
         * `FIXED` - 固定期限
         * `PERIODIC` - 周期性
        """
)
public enum LevelValidityType {
    
    PERMANENT("永久有效"),
    FIXED("固定期限"),
    PERIODIC("周期性");
    
    private final String description;
    
    LevelValidityType(String description) {
        this.description = description;
    }
} 