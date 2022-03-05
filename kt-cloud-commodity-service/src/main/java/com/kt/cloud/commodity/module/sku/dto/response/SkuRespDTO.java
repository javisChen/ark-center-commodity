package com.kt.cloud.commodity.module.sku.dto.response;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * sku
 * </p>
 *
 * @author EOP
 * @since 2022-03-05
 */
@Data
@ApiModel(value = "SkuRespDTO对象", description = "sku")
public class SkuRespDTO implements Serializable {


    @ApiModelProperty(value = "SpuId，关联co_spu.id", required = true)
    private Long spuId;

    @ApiModelProperty(value = "spu编码", required = true)
    private String sn;

    @ApiModelProperty(value = "销售价（单位：分）", required = true)
    private Integer salesPrice;

    @ApiModelProperty(value = "成本价（单位：分）", required = true)
    private Integer costPrice;

    @ApiModelProperty(value = "库存", required = true)
    private Integer stock;

    @ApiModelProperty(value = "预警库存", required = true)
    private Integer warnStock;

    @ApiModelProperty(value = "销售参数JSON", required = true)
    private String paramData;

}
