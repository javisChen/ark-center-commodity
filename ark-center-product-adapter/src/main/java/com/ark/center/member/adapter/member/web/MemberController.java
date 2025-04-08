package com.ark.center.member.adapter.member.web;

import com.ark.center.member.app.member.MemberCommandHandler;
import com.ark.center.member.client.member.MemberCommandApi;
import com.ark.center.member.client.member.MemberQueryApi;
import com.ark.center.member.client.member.command.MemberRegisterCommand;
import com.ark.center.member.client.member.dto.MemberAuthDTO;
import com.ark.center.member.client.member.dto.MemberRegisterDTO;
import com.ark.center.member.infra.member.service.MemberAuthService;
import com.ark.component.dto.SingleResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/members")
@Tag(name = "会员管理接口")
public class MemberController implements MemberCommandApi, MemberQueryApi {
    
    private final MemberCommandHandler memberCommandHandler;
    private final MemberAuthService memberAuthService;
    
    @Override
    public SingleResponse<MemberRegisterDTO> register(@RequestBody @Validated MemberRegisterCommand command) {
        MemberRegisterDTO registerDTO = memberCommandHandler.register(command);
        return SingleResponse.ok(registerDTO);
    }
    
    @Override
    public SingleResponse<MemberAuthDTO> getMemberAuthInfo(String identity) {
        MemberAuthDTO memberAuthDTO = memberAuthService.getMemberAuthByIdentity(identity);
        return SingleResponse.ok(memberAuthDTO);
    }
    
}