package com.ark.center.member.infra.point;

import com.ark.center.member.client.member.common.PointsAccountStatus;
import com.ark.component.orm.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("me_member_points_account")
public class MemberPointsAccount extends BaseEntity {
    
    /**
     * 会员ID
     */
    @TableField("member_id")
    private Long memberId;
    
    /**
     * 可用积分
     */
    @TableField("available_points")
    private Long availablePoints;
    
    /**
     * 冻结积分
     */
    @TableField("frozen_points")
    private Long frozenPoints;
    
    /**
     * 累计获得积分
     */
    @TableField("total_points")
    private Long totalPoints;
    
    /**
     * 累计使用积分
     */
    @TableField("used_points")
    private Long usedPoints;
    
    /**
     * 累计过期积分
     */
    @TableField("expired_points")
    private Long expiredPoints;
    
    /**
     * 账户状态
     */
    @TableField("account_status")
    private PointsAccountStatus accountStatus;
} 