package com.ark.center.product.client.inventory.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "StockQuantityDTO", description = "库存查询响应模型")
public class StockQuantityDTO {

    @Schema(name = "skuId", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long skuId;

    @Schema(name = "库存量", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer stock;
}
