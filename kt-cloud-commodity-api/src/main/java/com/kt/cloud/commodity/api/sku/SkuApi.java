package com.kt.cloud.commodity.api.sku;

import com.kt.cloud.commodity.api.sku.request.SkuInfoGetReqDTO;
import com.kt.cloud.commodity.api.sku.response.SkuRespDTO;
import com.kt.component.dto.MultiResponse;
import com.kt.component.microservice.rpc.exception.FeignCommonErrorDecoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(
        name = "${kt.cloud.commodity.service.name:commodity}",
        path = "/v1/sku",
        url = "${kt.cloud.commodity.service.uri:}",
        decode404 = true,
        configuration = FeignCommonErrorDecoder.class
)
public interface SkuApi {

    @PostMapping("/list")
    MultiResponse<SkuRespDTO> getSkuInfoList(SkuInfoGetReqDTO request);
}
