package com.ark.center.product.infra.spu;

import com.ark.center.product.infra.sku.typehandler.SpuAttrTypeHandler;
import com.ark.component.orm.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * <p>
 * spu销售属性
 * </p>
 *
 * @author EOP
 * @since 2022-03-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "spu_sales", autoResultMap = true)
public class SpuSales extends BaseEntity {


    /**
     * spuId，关联spu.id
     */
    @TableField("spu_id")
    private Long spuId;

    /**
     * 运费模版ID,关联freight_template.id
     */
    @TableField("freight_template_id")
    private Long freightTemplateId;

    /**
     * PC端商品介绍富文本
     */
    @TableField("pc_rich_text")
    private String pcRichText;

    /**
     * 移动端商品介绍富文本
     */
    @TableField("mobile_rich_text")
    private String mobileRichText;

    /**
     * 参数属性JSON
     */
    @TableField(value = "param_data", typeHandler = SpuAttrTypeHandler.class)
    private List<SpuAttr> paramData;

}
