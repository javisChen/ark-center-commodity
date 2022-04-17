package com.kt.cloud.commodity.module.commodity.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
/**
 * <p>
 * sku
 * </p>
 *
 * @author EOP
 * @since 2022-03-05
 */
@Data
@ApiModel(value = "SkuUpdateReqDTO对象", description = "sku")
public class SkuUpdateReqDTO implements Serializable {


    @ApiModelProperty(value = "SpuId，关联co_spu.id", required = true)
    @NotEmpty(message = "SpuId，关联co_spu.id不能为空")
    private Long spuId;

    @ApiModelProperty(value = "spu编码", required = true)
    @NotEmpty(message = "spu编码不能为空")
    private String sn;

    @ApiModelProperty(value = "销售价（单位：分）", required = true)
    @NotNull(message = "销售价（单位：分）不能为空")
    private Integer salesPrice;

    @ApiModelProperty(value = "成本价（单位：分）", required = true)
    @NotNull(message = "成本价（单位：分）不能为空")
    private Integer costPrice;

    @ApiModelProperty(value = "库存", required = true)
    @NotNull(message = "库存不能为空")
    private Integer stock;

    @ApiModelProperty(value = "预警库存", required = true)
    @NotNull(message = "预警库存不能为空")
    private Integer warnStock;

    @ApiModelProperty(value = "销售参数JSON", required = true)
    @NotEmpty(message = "销售参数JSON不能为空")
    private String paramData;

}