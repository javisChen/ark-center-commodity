package com.ark.center.member.client.member.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(
    enumAsRef = true, 
    description = """
        注册类型:
         * `USERNAME` - 用户名注册
         * `MOBILE` - 手机号注册
         * `EMAIL` - 邮箱注册
         * `WECHAT` - 微信注册
         * `QQ` - QQ注册
        """
)
public enum RegisterType {
    
    USERNAME("用户名注册"),
    MOBILE("手机号注册"),
    EMAIL("邮箱注册"),
    WECHAT("微信注册"),
    QQ("QQ注册");
    
    private final String description;
    
    RegisterType(String description) {
        this.description = description;
    }
} 