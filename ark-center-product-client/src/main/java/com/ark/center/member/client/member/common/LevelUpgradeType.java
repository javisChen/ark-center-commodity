package com.ark.center.member.client.member.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(
    enumAsRef = true, 
    description = """
        等级升级类型:
         * `AUTO` - 自动升级
         * `MANUAL` - 手动调整
        """
)
public enum LevelUpgradeType {
    
    AUTO("自动升级"),
    MANUAL("手动调整");
    
    private final String description;
    
    LevelUpgradeType(String description) {
        this.description = description;
    }
} 