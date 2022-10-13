package com.ark.center.commodity.api.sku;

import com.ark.center.commodity.api.sku.request.SkuInfoGetReqDTO;
import com.ark.center.commodity.api.sku.response.SkuRespDTO;
import com.ark.component.dto.MultiResponse;
import com.ark.component.microservice.rpc.exception.FeignCommonErrorDecoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "${ark.center.commodity.service.name:commodity}",
        path = "/v1/sku",
        url = "${ark.center.commodity.service.uri:}",
        decode404 = true,
        configuration = FeignCommonErrorDecoder.class
)
public interface SkuApi {

    @PostMapping("/list")
    MultiResponse<SkuRespDTO> getSkuInfoList(@RequestBody SkuInfoGetReqDTO request);
}
