package com.ark.center.commodity.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ark.component.orm.mybatis.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * <p>
 * 商品属性组
 * </p>
 *
 * @author EOP
 * @since 2022-03-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("co_attr_group")
public class AttrGroupDO extends BaseEntity {


    /**
     * 属性分组名称
     */
    @TableField("`name`")
    private String name;

    /**
     * 模板ID，关联co_attr_template.id
     */
    @TableField("attr_template_id")
    private Long attrTemplateId;

}
