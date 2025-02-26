package com.ark.center.member.client.member.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(
    enumAsRef = true, 
    description = """
        性别:
         * `UNKNOWN` - 未知
         * `MALE` - 男
         * `FEMALE` - 女
        """
)
public enum Gender {
    
    UNKNOWN("未知"),
    MALE("男"),
    FEMALE("女");
    
    private final String description;
    
    Gender(String description) {
        this.description = description;
    }
} 