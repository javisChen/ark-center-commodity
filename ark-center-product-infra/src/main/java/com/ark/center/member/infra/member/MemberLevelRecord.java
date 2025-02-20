package com.ark.center.member.infra.member;

import com.ark.center.member.client.member.common.LevelUpgradeType;
import com.ark.component.orm.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("me_member_level_record")
public class MemberLevelRecord extends BaseEntity {
    
    /**
     * 会员ID
     */
    @TableField("member_id")
    private Long memberId;
    
    /**
     * 变更前等级
     */
    @TableField("before_level")
    private Integer beforeLevel;
    
    /**
     * 变更后等级
     */
    @TableField("after_level")
    private Integer afterLevel;
    
    /**
     * 升级类型
     */
    @TableField("upgrade_type")
    private LevelUpgradeType upgradeType;
    
    /**
     * 生效时间
     */
    @TableField("effect_time")
    private LocalDateTime effectTime;
    
    /**
     * 过期时间
     */
    @TableField("expire_time")
    private LocalDateTime expireTime;
    
    /**
     * 描述
     */
    @TableField("description")
    private String description;
} 