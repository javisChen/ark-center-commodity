package com.ark.center.member.client.member.dto;

import com.ark.center.member.client.member.common.IdentityType;
import com.ark.center.member.client.member.common.MemberStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 会员认证信息DTO，提供给认证中心使用
 */
@Data
@Schema(description = "会员认证信息DTO")
public class MemberAuthDTO {
    
    /**
     * 会员ID
     */
    @Schema(description = "会员ID", example = "10001")
    private Long memberId;
    
    /**
     * 用户名
     */
    @Schema(description = "用户名", example = "user123")
    private String username;
    
    /**
     * 手机号
     */
    @Schema(description = "手机号", example = "13800138000")
    private String mobile;
    
    /**
     * 密码(加密后)
     */
    @Schema(description = "密码(加密后)", example = "e10adc3949ba59abbe56e057f20f883e")
    private String password;
    
    /**
     * 会员状态
     */
    @Schema(description = "会员状态", example = "NORMAL")
    private MemberStatus memberStatus;
    
    /**
     * 认证类型
     */
    @Schema(description = "认证类型", example = "MOBILE")
    private IdentityType identityType;
    
    /**
     * 认证标识
     */
    @Schema(description = "认证标识(用户名/手机号/邮箱等)", example = "13800138000")
    private String identifier;
    
    /**
     * 最后登录时间
     */
    @Schema(description = "最后登录时间", example = "2023-01-01T12:00:00")
    private LocalDateTime lastLoginTime;
} 