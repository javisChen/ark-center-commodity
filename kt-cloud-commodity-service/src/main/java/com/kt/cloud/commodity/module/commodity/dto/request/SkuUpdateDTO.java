package com.kt.cloud.commodity.module.commodity.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SkuUpdateDTO {

    @ApiModelProperty(value = "SKU列表", required = true)
    private List<SkuAttrUpdateDTO> skuAttrList;
    @ApiModelProperty(value = "商品重量，默认为克(g)", required = false)
    private String code;
    @ApiModelProperty(value = "商品重量，默认为克(g)", required = false)
    private Integer salesPrice;
    @ApiModelProperty(value = "商品重量，默认为克(g)", required = false)
    private Integer costPrice;
    @ApiModelProperty(value = "商品重量，默认为克(g)", required = false)
    private Integer stock;
    @ApiModelProperty(value = "商品重量，默认为克(g)", required = false)
    private Integer warnStock;

}