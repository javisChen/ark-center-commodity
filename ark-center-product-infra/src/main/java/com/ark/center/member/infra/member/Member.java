package com.ark.center.member.infra.member;

import com.ark.center.member.client.member.common.Gender;
import com.ark.center.member.client.member.common.MemberStatus;
import com.baomidou.mybatisplus.annotation.*;
import com.ark.component.orm.mybatis.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("me_member")
public class Member extends BaseEntity {
    
    /**
     * 手机号
     */
    @TableField("mobile")
    private String mobile;
    
    /**
     * 昵称
     */
    @TableField("nickname")
    private String nickname;
    
    /**
     * 头像URL
     */
    @TableField("avatar")
    private String avatar;
    
    /**
     * 性别
     */
    @TableField("gender")
    private Gender gender;
    
    /**
     * 出生日期
     */
    @TableField("birth_date")
    private LocalDate birthDate;
    
    /**
     * 会员等级
     */
    @TableField("level")
    private Integer level;
    
    /**
     * 成长值
     */
    @TableField("growth_value")
    private Long growthValue;
    
    /**
     * 积分
     */
    @TableField("points")
    private Long points;
    
    /**
     * 状态
     */
    @TableField("status")
    private MemberStatus status;
} 