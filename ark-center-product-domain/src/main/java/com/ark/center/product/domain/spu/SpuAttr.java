package com.ark.center.product.domain.spu;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ark.component.orm.mybatis.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * <p>
 * spu参数属性
 * </p>
 *
 * @author EOP
 * @since 2022-04-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("spu_attr")
public class SpuAttr extends BaseEntity {


    /**
     * spuId，关联spu.id
     */
    @TableField("spu_id")
    private Long spuId;

    /**
     * 属性id
     */
    @TableField("attr_id")
    private Long attrId;

    /**
     * 属性名称（冗余）
     */
    @TableField("attr_name")
    private String attrName;

    /**
     * 属性值（冗余）
     */
    @TableField("attr_value")
    private String attrValue;

}
