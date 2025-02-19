package com.ark.center.member.adapter.member.web;

import com.ark.center.member.client.member.MemberCommandApi;
import com.ark.center.member.client.member.command.MemberRegisterCommand;
import com.ark.center.member.app.member.MemberCommandHandler;
import com.ark.component.dto.SingleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController implements MemberCommandApi {
    
    private final MemberCommandHandler memberCommandHandler;
    
    @Override
    public SingleResponse<Long> register(@Validated MemberRegisterCommand command) {
        return SingleResponse.ok(memberCommandHandler.register(command));
    }
} 