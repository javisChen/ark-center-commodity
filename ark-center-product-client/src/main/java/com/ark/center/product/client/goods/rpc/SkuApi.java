package com.ark.center.product.client.goods.rpc;

import com.ark.center.product.client.goods.dto.SkuDTO;
import com.ark.center.product.client.goods.query.SkuQry;
import com.ark.component.dto.MultiResponse;
import com.ark.component.microservice.rpc.exception.FeignCommonErrorDecoder;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "${ark.center.commodity.service.name:commodity}",
        path = "/v1/inner/sku",
        url = "${ark.center.commodity.service.uri:}",
        dismiss404 = true,
        configuration = FeignCommonErrorDecoder.class
)
public interface SkuApi {

    @Operation(summary = "批量查询")
    @PostMapping("/list")
    MultiResponse<SkuDTO> querySkus(@RequestBody SkuQry qry);

}