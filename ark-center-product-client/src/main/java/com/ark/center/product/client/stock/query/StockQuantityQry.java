package com.ark.center.product.client.stock.query;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "StockQuantityQry", description = "库存查询模型")
public class StockQuantityQry {

    @Size(max = 1000, message = "每次限制获取1000条SKU信息")
    private List<Long> skuIds;

}
