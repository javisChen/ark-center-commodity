package com.ark.center.member.client.member.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(
    enumAsRef = true, 
    description = """
        注册类型:
         * `MOBILE` - 手机号注册 (code=1)
         * `USERNAME` - 用户名注册 (code=2)
        """
)
public enum RegisterType {
    MOBILE(1, "手机号注册"),
    USERNAME(2, "用户名注册");
    
    private final int code;
    private final String description;
    
    RegisterType(int code, String description) {
        this.code = code;
        this.description = description;
    }
} 