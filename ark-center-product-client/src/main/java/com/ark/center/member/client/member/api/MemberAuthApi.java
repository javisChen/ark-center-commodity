package com.ark.center.member.client.member.api;

import com.ark.center.member.client.member.MemberQueryApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "ark-center-member", contextId = "memberAuthApi", path = "/api/member")
public interface MemberAuthApi extends MemberQueryApi {
    // 继承了MemberQueryApi中的所有方法，无需额外定义
} 