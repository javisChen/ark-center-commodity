package com.ark.center.product.infra.attr;

import com.ark.component.orm.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("pro_attr_group")
public class AttrGroup extends BaseEntity {


    /**
     * 属性分组名称
     */
    @TableField("`name`")
    private String name;

    /**
     * 模板ID，关联attr_template.id
     */
    @TableField("attr_template_id")
    private Long attrTemplateId;

}
