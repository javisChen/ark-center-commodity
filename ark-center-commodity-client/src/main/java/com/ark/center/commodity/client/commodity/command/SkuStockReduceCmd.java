package com.ark.center.commodity.client.commodity.command;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel(value = "SkuStockReduceCmd", description = "SKU库存扣减对象")
@Data
public class SkuStockReduceCmd {

    @ApiModelProperty(value = "SkuId", required = true)
    @NotBlank(message = "SkuId不能为空")
    private Long id;

    @ApiModelProperty(value = "库存扣减数量", required = true)
    @NotNull(message = "库存扣减数量不能为空")
    @Min(value = 1, message = "库存扣减数量必须大于0")
    private Integer quantity;

}
