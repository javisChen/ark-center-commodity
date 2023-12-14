package com.ark.center.product.client.stock;

import com.ark.center.product.client.stock.command.StockDecreaseCmd;
import com.ark.center.product.client.stock.dto.StockDecreaseDTO;
import com.ark.center.product.client.stock.dto.StockQuantityDTO;
import com.ark.center.product.client.stock.query.StockQuantityQry;
import com.ark.component.dto.MultiResponse;
import com.ark.component.dto.SingleResponse;
import com.ark.component.microservice.rpc.exception.FeignCommonErrorDecoder;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 库存接口定义
 */
@FeignClient(
        name = "${ark.center.commodity.service.name:commodity}",
        path = "/v1/inner/stock",
        url = "${ark.center.commodity.service.uri:}",
        dismiss404 = true,
        configuration = FeignCommonErrorDecoder.class
)
public interface StockApi {

    @Operation(summary = "查询商品库存数量")
    @PostMapping("/quantity")
    MultiResponse<List<StockQuantityDTO>> queryStockQuantity(@Validated @RequestBody StockQuantityQry qry);

    @Operation(summary = "库存扣减")
    @PostMapping("/decrease")
    SingleResponse<StockDecreaseDTO> decreaseStock(@RequestBody @Validated StockDecreaseCmd cmd);
}
