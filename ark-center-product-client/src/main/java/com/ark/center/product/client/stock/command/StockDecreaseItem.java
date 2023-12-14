package com.ark.center.product.client.stock.command;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StockDecreaseItem {

    @Schema(name = "skuId", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "skuId不能为空")
    private Long skuId;

    @Schema(name = "库存扣减数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "库存扣减数量不能为空")
    @Min(value = 1, message = "库存扣减数量必须大于0")
    private Integer quantity;

}
