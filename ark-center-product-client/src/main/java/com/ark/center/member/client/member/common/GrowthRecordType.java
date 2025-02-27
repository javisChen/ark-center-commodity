package com.ark.center.member.client.member.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(
    enumAsRef = true, 
    description = """
        成长值记录类型:
         * `INCREASE` - 增加成长值
         * `DECREASE` - 减少成长值
        """
)
public enum GrowthRecordType {
    
    INCREASE("增加成长值"),
    DECREASE("减少成长值");
    
    private final String description;
    
    GrowthRecordType(String description) {
        this.description = description;
    }
} 