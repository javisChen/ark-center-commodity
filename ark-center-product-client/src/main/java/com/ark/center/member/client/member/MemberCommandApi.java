package com.ark.center.member.client.member;

import com.ark.center.member.client.member.command.MemberRegisterCommand;
import com.ark.component.dto.SingleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.validation.annotation.Validated;

@FeignClient(
    name = "${ark.center.member.service.name:member}",
    path = "/v1/members",
    url = "${ark.center.member.service.uri:}",
    dismiss404 = true
)
@Tag(name = "会员命令接口")
public interface MemberCommandApi {

    @PostMapping("/register")
    @Operation(summary = "会员注册")
    SingleResponse<Long> register(@RequestBody @Validated MemberRegisterCommand command);
} 