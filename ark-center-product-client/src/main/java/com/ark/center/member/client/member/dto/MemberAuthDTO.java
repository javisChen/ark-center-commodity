package com.ark.center.member.client.member.dto;

import com.ark.center.member.client.member.common.IdentityType;
import com.ark.center.member.client.member.common.MemberStatus;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 会员认证信息DTO，提供给认证中心使用
 */
@Data
public class MemberAuthDTO {
    
    /**
     * 会员ID
     */
    private Long memberId;
    
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 手机号
     */
    private String mobile;
    
    /**
     * 密码(加密后)
     */
    private String password;
    
    /**
     * 会员状态
     */
    private MemberStatus memberStatus;
    
    /**
     * 认证类型
     */
    private IdentityType identityType;
    
    /**
     * 认证标识
     */
    private String identifier;
    
    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;
} 