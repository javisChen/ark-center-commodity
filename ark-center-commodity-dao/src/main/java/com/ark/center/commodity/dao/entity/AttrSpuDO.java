package com.ark.center.commodity.dao.entity;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * SPU特有的商品属性选项
 * </p>
 *
 * @author EOP
 * @since 2022-03-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("co_attr_spu")
public class AttrSpuDO extends BaseEntity {


    /**
     * 商品属性ID，关联co_attr.id
     */
    @TableField("attr_id")
    private Long attrId;

    /**
     * SPU ID，关联co_spu.id
     */
    @TableField("spu_id")
    private Long spuId;

    /**
     * 选项列表，有多个选项以逗号（,）分隔
     */
    @TableField("`options`")
    private String options;

}
