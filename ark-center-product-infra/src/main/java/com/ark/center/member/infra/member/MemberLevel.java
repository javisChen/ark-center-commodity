package com.ark.center.member.infra.member;

import com.ark.component.orm.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("me_member_level")
public class MemberLevel extends BaseEntity {
    
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
     * 等级
     */
    @TableField("level")
    private Integer level;
    
    /**
     * 升级所需成长值
     */
    @TableField("growth_value")
    private Long growthValue;
    
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