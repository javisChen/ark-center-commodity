package com.ark.center.product.client.inventory;

import com.ark.center.product.client.inventory.command.StockLockCmd;
import com.ark.center.product.client.inventory.dto.StockQuantityDTO;
import com.ark.center.product.client.inventory.query.StockQuantityQry;
import com.ark.component.dto.MultiResponse;
import com.ark.component.dto.ServerResponse;
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
        name = "${ark.center.product.service.name:product}",
        path = "/v1/inventory",
        url = "${ark.center.product.service.uri:}",
        dismiss404 = true,
        configuration = FeignCommonErrorDecoder.class
)
public interface InventoryApi {

    @Operation(summary = "查询商品库存数量")
    @PostMapping("/quantity")
    MultiResponse<List<StockQuantityDTO>> queryStockQuantity(@Validated @RequestBody StockQuantityQry qry);

    @Operation(summary = "库存扣减")
    @PostMapping("/lock")
    ServerResponse lock(@RequestBody @Validated StockLockCmd cmd);
}
