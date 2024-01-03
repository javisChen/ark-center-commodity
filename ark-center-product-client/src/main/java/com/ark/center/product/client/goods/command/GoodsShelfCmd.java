package com.ark.center.product.client.goods.command;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(name = "GoodsShelfCmd", description = "商品上下架命令")
public class GoodsShelfCmd implements Serializable {

    @Schema(name = "商品ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "商品ID不能为空")
    private Long id;

    @Schema(name = "上下架 0-下架 1-上架", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "请指定上/下架")
    private Integer shelfStatus;

}
