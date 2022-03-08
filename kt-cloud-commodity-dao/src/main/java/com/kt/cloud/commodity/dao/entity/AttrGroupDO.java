package com.kt.cloud.commodity.dao.entity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kt.component.db.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.kt.component.common.enums.BasicEnums;
import com.kt.component.common.enums.EnumUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
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
