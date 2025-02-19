package com.ark.center.member.client.member.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import com.ark.center.member.client.member.common.RegisterType;

@Data
@Schema(description = "会员注册命令")
public class MemberRegisterCommand {
    
    @Schema(description = "注册类型", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "注册类型不能为空")
    private RegisterType registerType;
    
    @Schema(description = "用户名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "用户名不能为空", groups = {UsernameRegister.class})
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_]{5,15}$", message = "用户名必须以字母开头，可包含数字和下划线，长度6-16位", groups = {UsernameRegister.class})
    private String username;
    
    @Schema(description = "手机号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "手机号不能为空", groups = {MobileRegister.class})
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确", groups = {MobileRegister.class})
    private String mobile;
    
    @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).{8,20}$", message = "密码必须包含字母和数字,长度8-20位")
    private String password;
    
    @Schema(description = "验证码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "验证码不能为空", groups = {MobileRegister.class})
    @Pattern(regexp = "^\\d{6}$", message = "验证码必须是6位数字", groups = {MobileRegister.class})
    private String verifyCode;

    @Schema(
        description = "验证码ID（生成验证码时返回的ID）",
        requiredMode = Schema.RequiredMode.REQUIRED,
        example = "sms_1234567890",
        title = "验证码唯一标识"
    )
    @NotBlank(message = "验证码ID不能为空", groups = {MobileRegister.class})
    private String verifyCodeId;
    
    public interface MobileRegister {}
    public interface UsernameRegister {}
} 