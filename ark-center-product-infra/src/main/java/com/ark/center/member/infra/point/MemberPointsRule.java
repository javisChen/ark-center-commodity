package com.ark.center.member.infra.point;

import com.ark.center.member.client.member.common.PointsCalcType;
import com.ark.center.member.client.member.common.PointsRuleType;
import com.ark.component.orm.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("me_member_points_rule")
public class MemberPointsRule extends BaseEntity {
    
    /**
     * 规则编码
     */
    @TableField("rule_code")
    private String ruleCode;
    
    /**
     * 规则名称
     */
    @TableField("rule_name")
    private String ruleName;
    
    /**
     * 规则类型
     */
    @TableField("rule_type")
    private PointsRuleType ruleType;
    
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
     * 积分值/比例
     */
    @TableField("points_value")
    private Integer pointsValue;
    
    /**
     * 计算类型
     */
    @TableField("calc_type")
    private PointsCalcType calcType;
    
    /**
     * 周期类型
     */
    @TableField("period_type")
    private Integer periodType;
    
    /**
     * 周期上限
     */
    @TableField("period_limit")
    private Long periodLimit;
    
    /**
     * 积分有效期(天)
     */
    @TableField("validity_days")
    private Integer validityDays;
    
    /**
     * 状态
     */
    @TableField("status")
    private Boolean status;
} 