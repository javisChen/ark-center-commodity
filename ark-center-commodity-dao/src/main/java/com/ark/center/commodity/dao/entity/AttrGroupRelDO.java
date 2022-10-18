package com.ark.center.commodity.dao.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ark.component.orm.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.ark.component.common.enums.BasicEnums;
import com.ark.component.common.enums.EnumUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
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
@TableName("co_attr_group_rel")
public class AttrGroupRelDO extends BaseEntity {


    /**
     * 属性ID，关联co_attr.id
     */
    @TableField("attr_id")
    private Long attrId;

    /**
     * 属性组ID，关联co_attr_group.id
     */
    @TableField("attr_group_id")
    private Long attrGroupId;

}
