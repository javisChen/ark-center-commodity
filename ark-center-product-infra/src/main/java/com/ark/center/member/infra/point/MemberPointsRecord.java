package com.ark.center.member.infra.point;

import com.ark.center.member.client.member.common.PointsRecordType;
import com.ark.component.orm.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("me_member_points_record")
public class MemberPointsRecord extends BaseEntity {
    
    /**
     * 会员ID
     */
    @TableField("member_id")
    private Long memberId;
    
    /**
     * 积分变动值
     */
    @TableField("points")
    private Long points;
    
    /**
     * 变动前积分
     */
    @TableField("before_points")
    private Long beforePoints;
    
    /**
     * 变动后积分
     */
    @TableField("after_points")
    private Long afterPoints;
    
    /**
     * 记录类型
     */
    @TableField("record_type")
    private PointsRecordType recordType;
    
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
     * 业务流水号
     */
    @TableField("biz_no")
    private String bizNo;
    
    /**
     * 过期时间
     */
    @TableField("expire_time")
    private LocalDateTime expireTime;
} 