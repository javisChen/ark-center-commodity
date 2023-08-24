package com.ark.center.commodity.client.commodity.command;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(name = "SkuStockReduceCmd", description = "SKU库存扣减对象")
@Data
public class SkuStockReduceCmd {

    @Schema(name = "SkuId", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "SkuId不能为空")
    private Long id;

    @Schema(name = "库存扣减数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "库存扣减数量不能为空")
    @Min(value = 1, message = "库存扣减数量必须大于0")
    private Integer quantity;

}
