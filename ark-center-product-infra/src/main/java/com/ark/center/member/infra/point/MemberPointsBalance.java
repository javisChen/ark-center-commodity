package com.ark.center.member.infra.point;

import com.ark.component.orm.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("me_member_points_balance")
public class MemberPointsBalance extends BaseEntity {
    
    /**
     * 会员ID
     */
    @TableField("member_id")
    private Long memberId;
    
    /**
     * 来源积分流水ID
     */
    @TableField("source_record_id")
    private Long sourceRecordId;
    
    /**
     * 总积分
     */
    @TableField("total_points")
    private Long totalPoints;
    
    /**
     * 可用积分
     */
    @TableField("available_points")
    private Long availablePoints;
    
    /**
     * 已使用积分
     */
    @TableField("used_points")
    private Long usedPoints;
    
    /**
     * 过期时间
     */
    @TableField("expire_time")
    private LocalDateTime expireTime;
} 