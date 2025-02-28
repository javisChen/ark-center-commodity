package com.ark.center.member.infra.channel;

import com.ark.component.orm.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("me_register_channel")
public class RegisterChannel extends BaseEntity {
    
    /**
     * 渠道编码
     */
    @TableField("channel_code")
    private String channelCode;
    
    /**
     * 渠道名称
     */
    @TableField("channel_name")
    private String channelName;
    
    /**
     * 渠道类型
     */
    @TableField("channel_type")
    private String channelType;
    
    /**
     * 渠道描述
     */
    @TableField("description")
    private String description;
    
    /**
     * 状态 0-禁用 1-启用
     */
    @TableField("status")
    private Boolean status;
} 