package com.kt.cloud.commodity.dao.entity;
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
 * spu销售属性
 * </p>
 *
 * @author EOP
 * @since 2022-03-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("co_spu_sales")
public class SpuSalesDO extends BaseEntity {


    /**
     * spuId，关联co_spu.id
     */
    @TableField("spu_id")
    private Long spuId;

    /**
     * spu编码
     */
    @TableField("sn")
    private String sn;

    /**
     * 运费模版ID,关联freight_template.id
     */
    @TableField("freight_template_id")
    private Long freightTemplateId;

    /**
     * PC端商品介绍富文本
     */
    @TableField("pc_detail_html")
    private String pcDetailHtml;

    /**
     * 移动端商品介绍富文本
     */
    @TableField("mobile_detail_html")
    private String mobileDetailHtml;

}
