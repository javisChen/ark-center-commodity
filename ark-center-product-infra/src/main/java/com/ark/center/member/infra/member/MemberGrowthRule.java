package com.ark.center.member.infra.member;

import com.ark.component.orm.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("me_member_growth_rule")
public class MemberGrowthRule extends BaseEntity {
    
    /**
     * 场景编码
     */
    @TableField("scene_code")
    private String sceneCode;
    
    /**
     * 场景名称
     */
    @TableField("scene_name")
    private String sceneName;
    
    /**
     * 成长值
     */
    @TableField("growth_value")
    private Long growthValue;
    
    /**
     * 周期类型 0-一次性 1-每天 2-每周 3-每月
     */
    @TableField("period_type")
    private Integer periodType;
    
    /**
     * 周期上限
     */
    @TableField("period_limit")
    private Long periodLimit;
    
    /**
     * 状态
     */
    @TableField("status")
    private Boolean status;
} 