package com.ark.center.member.infra.member;

import com.ark.component.orm.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("me_member_growth_record")
public class MemberGrowthRecord extends BaseEntity {
    
    /**
     * 会员ID
     */
    @TableField("member_id")
    private Long memberId;
    
    /**
     * 成长值变更
     */
    @TableField("growth_value")
    private Long growthValue;
    
    /**
     * 变更前值
     */
    @TableField("before_value")
    private Long beforeValue;
    
    /**
     * 变更后值
     */
    @TableField("after_value")
    private Long afterValue;
    
    /**
     * 场景编码
     */
    @TableField("scene_code")
    private String sceneCode;
    
    /**
     * 描述
     */
    @TableField("description")
    private String description;
    
    /**
     * 关联业务ID
     */
    @TableField("biz_id")
    private String bizId;
    
    /**
     * 关联业务类型
     */
    @TableField("biz_type")
    private String bizType;
} 