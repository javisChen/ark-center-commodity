package com.ark.center.member.infra.member;

import com.ark.center.member.client.member.common.LevelValidityType;
import com.ark.component.orm.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("me_member_level_config")
public class MemberLevelConfig extends BaseEntity {
    
    /**
     * 等级编码
     */
    @TableField("level_code")
    private String levelCode;
    
    /**
     * 等级名称
     */
    @TableField("level_name")
    private String levelName;
    
    /**
     * 等级图标
     */
    @TableField("level_icon")
    private String levelIcon;
    
    /**
     * 等级值
     */
    @TableField("level")
    private Integer level;
    
    /**
     * 升级所需成长值
     */
    @TableField("growth_value")
    private Long growthValue;
    
    /**
     * 升级所需消费金额
     */
    @TableField("consume_amount")
    private Long consumeAmount;
    
    /**
     * 升级所需订单数
     */
    @TableField("order_count")
    private Integer orderCount;
    
    /**
     * 是否允许自动升级
     */
    @TableField("auto_upgrade")
    private Boolean autoUpgrade;
    
    /**
     * 是否允许自动降级
     */
    @TableField("auto_downgrade")
    private Boolean autoDowngrade;
    
    /**
     * 保级所需成长值
     */
    @TableField("keep_growth_value")
    private Long keepGrowthValue;
    
    /**
     * 有效期类型
     */
    @TableField("validity_type")
    private LevelValidityType validityType;
    
    /**
     * 固定有效期-开始时间
     */
    @TableField("valid_start_time")
    private LocalDateTime validStartTime;
    
    /**
     * 固定有效期-结束时间
     */
    @TableField("valid_end_time")
    private LocalDateTime validEndTime;
    
    /**
     * 周期类型 1-天 2-周 3-月 4-年
     */
    @TableField("period_type")
    private Integer periodType;
    
    /**
     * 周期值
     */
    @TableField("period_value")
    private Integer periodValue;
    
    /**
     * 是否为默认等级
     */
    @TableField("is_default")
    private Boolean isDefault;
    
    /**
     * 状态
     */
    @TableField("status")
    private Boolean status;
} 