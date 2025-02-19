package com.ark.center.auth.infra.verifycode.gateway;

import com.ark.center.auth.infra.api.ApiMeta;

import java.util.List;

public interface ApiGateway {

    List<ApiMeta> queryApis();

    ApiMeta getApi(Long apiId);

}
