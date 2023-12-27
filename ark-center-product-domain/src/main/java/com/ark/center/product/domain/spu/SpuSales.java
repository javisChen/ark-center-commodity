package com.ark.center.product.domain.spu;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ark.component.orm.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@TableName("spu_sales")
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
    @TableField("param_data")
    private String paramData;

}