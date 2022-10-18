package com.ark.center.commodity.dao.entity;

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
@TableName("co_spu_attr")
public class SpuAttrDO extends BaseEntity {


    /**
     * spuId，关联co_spu.id
     */
    @TableField("spu_id")
    private Long spuId;

    /**
     * 属性值（冗余）
     */
    @TableField("attr_value")
    private String attrValue;

}
