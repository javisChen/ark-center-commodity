package com.kt.cloud.commodity.module.commodity.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * CommodityPageRespDTO
 * </p>
 *
 * @author EOP
 * @since 2022-03-05
 */
@Data
@ApiModel(value = "CommodityPageRespDTO对象", description = "CommodityPageRespDTO")
public class SkuRespDTO implements Serializable {

    @ApiModelProperty(value = "skuId", required = false)
    private Long id;
    @ApiModelProperty(value = "商品重量，默认为克(g)", required = false)
    private String code;
    @ApiModelProperty(value = "销售价（单位：分）", required = false)
    private Integer salesPrice;
    @ApiModelProperty(value = "成本价（单位：分）", required = false)
    private Integer costPrice;
    @ApiModelProperty(value = "库存", required = false)
    private Integer stock;
    @ApiModelProperty(value = "预警库存", required = false)
    private Integer warnStock;
    @ApiModelProperty(value  = "SKU规格属性列表", required = true)
    private List<AttrRespDTO> specList;

}
