package com.ark.center.product.client.stock.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "StockDecreaseDTO", description = "库存扣减结果模型")
public class StockDecreaseDTO {

    @Schema(name = "扣减结果", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean result = Boolean.TRUE;

    @Schema(name = "库存不足的Sku集合，当result为false时会可能会有数据", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<Long> skuIds;

}
