package com.ark.center.commodity.infrastructure.attr.repository.db;

import com.ark.component.orm.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * <p>
 * 商品属性模板
 * </p>
 *
 * @author EOP
 * @since 2022-03-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("co_attr_template")
public class AttrTemplateDO extends BaseEntity {


    /**
     * 模板名称
     */
    @TableField("`name`")
    private String name;

    /**
     * 规格数量
     */
    @TableField("spec_count")
    private Integer specCount;

    /**
     * 参数数量
     */
    @TableField("param_count")
    private Integer paramCount;

}
