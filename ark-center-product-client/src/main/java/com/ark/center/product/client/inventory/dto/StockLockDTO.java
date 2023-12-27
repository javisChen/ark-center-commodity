package com.ark.center.product.client.inventory.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "StockLockDTO", description = "库存锁定结果")
public class StockLockDTO {

    @Schema(name = "扣减结果", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean result = true;

    @Schema(name = "库存不足的skuId，当result为false时会可能会存在", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long skuId;

}
