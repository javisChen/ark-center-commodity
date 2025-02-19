package com.ark.center.auth.infra.verifycode;

import com.ark.center.auth.client.verifycode.api.VerifyCodeApi;
import com.ark.component.microservice.rpc.exception.FeignCommonErrorDecoder;
import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(
        name = "${ark.center.auth.service.name:iam}",
        path = "/v1/verify-code",
        url = "${ark.center.auth.service.uri:}",
        dismiss404 = true,
        configuration = {FeignCommonErrorDecoder.class}
)
public interface VerifyCodeFacade extends VerifyCodeApi {

}
