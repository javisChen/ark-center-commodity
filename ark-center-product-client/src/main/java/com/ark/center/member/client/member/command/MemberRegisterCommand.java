package com.ark.center.member.client.member.command;

import com.ark.center.member.client.member.common.RegisterType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
    description = "会员注册请求",
    title = "会员注册命令",
    example = """
            {
              "mobile": "13800138000",
              "password": "password123",
              "nickname": "用户昵称",
              "gender": "MALE",
              "birthDate": "1990-01-01",
              "verifyCode": "123456",
              "verifyCodeId": "sms_1234567890"
            }
            """
)
public class MemberRegisterCommand {
    
    @Schema(description = "注册类型", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "注册类型不能为空")
    private RegisterType registerType;
    
    @Schema(description = "用户名", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_]{5,15}$", message = "用户名必须以字母开头，可包含数字和下划线，长度6-16位")
    private String username;
    
    @Schema(
        description = "手机号",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED,
        example = "13800138000",
        pattern = "^1[3-9]\\d{9}$",
        title = "会员手机号"
    )
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "请输入正确的手机号")
    private String mobile;
    
    @Schema(
        description = "密码",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED,
        example = "password123",
        minLength = 6,
        maxLength = 20,
        title = "会员密码"
    )
    @Size(min = 6, max = 20, message = "密码长度必须在6-20位之间")
    private String password;

    @Schema(
        description = "验证码",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED,
        example = "123456",
        pattern = "^\\d{4,6}$",
        title = "短信验证码"
    )
    @Pattern(regexp = "^\\d{4,6}$", message = "验证码格式不正确")
    private String verifyCode;

    @Schema(
        description = "验证码ID",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED,
        example = "sms_1234567890",
        title = "验证码唯一标识"
    )
    private String verifyCodeId;

    @Schema(
        description = "注册渠道编码",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED,
        example = "OFFICIAL_WEB",
        title = "注册渠道"
    )
    private String registerChannel;
} 