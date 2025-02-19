package com.ark.center.product.infra.attr;

import com.ark.component.orm.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 商品属性组与属性关联表
 * </p>
 *
 * @author EOP
 * @since 2022-03-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pro_attr_group_rel")
public class AttrGroupRel extends BaseEntity {


    /**
     * 属性ID，关联attr.id
     */
    @TableField("attr_id")
    private Long attrId;

    /**
     * 属性组ID，关联attr_group.id
     */
    @TableField("attr_group_id")
    private Long attrGroupId;

}
