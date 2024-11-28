package com.ark.center.product.client.goods.mq;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GoodsChangedEventDTO {

    @Schema(name = "spuId", description = "spuId", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long spuId;

    @Schema(name = "上下架状态", description = "上下架状态 0-下架 1-上架", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer shelfStatus;
}
