package com.ark.center.member.infra.member;

import com.ark.center.member.client.member.common.PointsCalcType;
import com.ark.component.orm.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("me_member_points_record_detail")
public class MemberPointsRecordDetail extends BaseEntity {
    
    /**
     * 积分流水ID
     */
    @TableField("record_id")
    private Long recordId;
    
    /**
     * 会员ID
     */
    @TableField("member_id")
    private Long memberId;
    
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
     * 计算类型
     */
    @TableField("calc_type")
    private PointsCalcType calcType;
    
    /**
     * 计算值/比例
     */
    @TableField("calc_value")
    private Integer calcValue;
    
    /**
     * 基础值
     */
    @TableField("base_value")
    private Long baseValue;
    
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
    
    /**
     * 额外信息
     */
    @TableField("extra_info")
    private String extraInfo;
} 