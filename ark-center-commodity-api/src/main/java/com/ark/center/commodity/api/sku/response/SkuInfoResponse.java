package com.ark.center.commodity.api.sku.response;

import lombok.Data;

import java.util.List;

@Data
public class SkuInfoResponse {

    /**
     * skuId
     */
    private Long skuId;

    /**
     * 销售价（单位：分）
     */
    private Integer salesPrice;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * SKU图片地址
     */
    private String picture;

    /**
     * 销售参数
     */
    private List<SkuAttrResponse> skuAttrs;
}
