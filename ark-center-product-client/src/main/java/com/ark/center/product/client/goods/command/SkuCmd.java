package com.ark.center.product.client.goods.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class SkuCmd {
    
    @Schema(name = "skuId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long id;
    @Schema(name = "sku编码", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String code;
    @Schema(name = "销售价（单位：分）", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer salesPrice;
    @Schema(name = "成本价（单位：分）", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer costPrice;
    @Schema(name = "库存", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer stock;
    @Schema(name = "预警库存", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer warnStock;
    @Schema(name  = "SKU规格属性列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<AttrCmd> specs;

}