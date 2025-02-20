package com.ark.center.member.infra.member;

import com.ark.center.member.client.member.common.IdentityType;
import com.baomidou.mybatisplus.annotation.*;
import com.ark.component.orm.mybatis.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("me_member_auth")
public class MemberAuth extends BaseEntity {
    
    /**
     * 会员ID
     */
    @TableField("member_id")
    private Long memberId;
    
    /**
     * 认证类型
     */
    @TableField("identity_type")
    private IdentityType identityType;
    
    /**
     * 认证标识（手机号、邮箱、用户名、微信openid、QQ openid）
     */
    @TableField("identifier")
    private String identifier;
    
    /**
     * 密码凭证
     */
    @TableField("credential")
    private String credential;
} 