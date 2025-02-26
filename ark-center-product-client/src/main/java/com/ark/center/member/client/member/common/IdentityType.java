package com.ark.center.member.client.member.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(
    enumAsRef = true, 
    description = """
        认证类型:
         * `USERNAME` - 用户名认证
         * `MOBILE` - 手机号认证
         * `EMAIL` - 邮箱认证
         * `WECHAT` - 微信认证
         * `QQ` - QQ认证
        """
)
public enum IdentityType {
    
    MOBILE("手机号认证"),
    EMAIL("邮箱认证"),
    USERNAME("用户名认证"),
    WECHAT("微信认证"),
    QQ("QQ认证");
    
    private final String description;
    
    IdentityType(String description) {
        this.description = description;
    }
} 