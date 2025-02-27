package com.ark.center.member.client.member.dto;

import com.ark.center.member.client.member.common.IdentityType;
import com.ark.center.member.client.member.common.MemberStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

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
     * 会员编号
     */
    @Schema(description = "会员编号", example = "M2023080100001")
    private String memberNo;
    
    /**
     * 昵称
     */
    @Schema(description = "昵称", example = "用户昵称")
    private String nickname;
    
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
    @Schema(description = "会员状态", example = "ENABLED")
    private MemberStatus status;
    
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
} 