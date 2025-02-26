package com.ark.center.member.adapter.member.web;

import com.ark.center.member.client.member.MemberCommandApi;
import com.ark.center.member.client.member.MemberQueryApi;
import com.ark.center.member.client.member.command.MemberRegisterCommand;
import com.ark.center.member.client.member.dto.MemberAuthDTO;
import com.ark.center.member.app.member.MemberCommandHandler;
import com.ark.center.member.infra.member.service.MemberAuthService;
import com.ark.component.dto.SingleResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
@Tag(name = "会员管理接口")
public class MemberController implements MemberCommandApi, MemberQueryApi {
    
    private final MemberCommandHandler memberCommandHandler;
    private final MemberAuthService memberAuthService;
    
    @Override
    public SingleResponse<Long> register(@Validated MemberRegisterCommand command) {
        return SingleResponse.ok(memberCommandHandler.register(command));
    }
    
    @Override
    public SingleResponse<MemberAuthDTO> getMemberAuthInfo(String identity) {
        MemberAuthDTO memberAuthDTO = memberAuthService.getMemberAuthByIdentity(identity);
        return SingleResponse.ok(memberAuthDTO);
    }
} 