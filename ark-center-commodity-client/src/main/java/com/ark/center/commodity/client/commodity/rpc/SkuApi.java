package com.ark.center.commodity.client.commodity.rpc;

import com.ark.center.commodity.client.commodity.command.SkuStockReduceCmd;
import com.ark.center.commodity.client.commodity.dto.SkuDTO;
import com.ark.center.commodity.client.commodity.query.SkuQry;
import com.ark.component.dto.MultiResponse;
import com.ark.component.dto.ServerResponse;
import com.ark.component.microservice.rpc.exception.FeignCommonErrorDecoder;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
        name = "${ark.center.commodity.service.name:commodity}",
        path = "/v1/sku",
        url = "${ark.center.commodity.service.uri:}",
        dismiss404 = true,
        configuration = FeignCommonErrorDecoder.class
)
public interface SkuApi {

    @Operation(summary = "SKU-列表查询")
    @PostMapping("/list")
    MultiResponse<SkuDTO> listSku(@RequestBody SkuQry qry);

    @Operation(summary = "SKU-扣减库存")
    @PostMapping("/stock/reduce")
    ServerResponse decreaseStock(@RequestBody List<SkuStockReduceCmd> cmd);
}
