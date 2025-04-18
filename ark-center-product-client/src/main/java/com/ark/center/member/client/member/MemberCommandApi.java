package com.ark.center.member.client.member;

import com.ark.center.member.client.member.command.MemberRegisterCommand;
import com.ark.center.member.client.member.dto.MemberRegisterDTO;
import com.ark.component.dto.SingleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
    name = "${ark.center.member.service.name:product}",
    path = "/v1/members",
    url = "${ark.center.member.service.uri:}",
    dismiss404 = true
)
@Tag(name = "会员命令接口")
public interface MemberCommandApi {

    /**
     * 会员注册
     *
     * @param command 注册命令
     * @return 会员ID
     */
    @PostMapping("/register")
    @Operation(summary = "会员注册", description = "注册新会员")
    SingleResponse<MemberRegisterDTO> register(@Parameter(description = "注册信息") @RequestBody @Validated MemberRegisterCommand command);
} 