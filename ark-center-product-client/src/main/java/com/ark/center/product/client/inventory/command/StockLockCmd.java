package com.ark.center.product.client.inventory.command;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.StringJoiner;

@Schema(name = "StockLockCmd", description = "库存锁定命令")
@Data
public class StockLockCmd {

    private List<Item> items;

    @Setter
    @Getter
    @AllArgsConstructor
    public static class Item {

        @Schema(name = "skuId", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "skuId不能为空")
        private Long skuId;

        @Schema(name = "库存锁定数量", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "库存锁定数量不能为空")
        @Min(value = 1, message = "库存锁定数量必须大于0")
        private Integer quantity;

        @Override
        public String toString() {
            return new StringJoiner(", ")
                    .add("skuId=" + skuId)
                    .add("quantity=" + quantity)
                    .toString();
        }
    }

}
