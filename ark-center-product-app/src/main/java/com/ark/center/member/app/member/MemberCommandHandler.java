package com.ark.center.member.app.member;

import com.ark.center.member.app.member.service.MemberRegisterService;
import com.ark.center.member.client.member.command.MemberRegisterCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MemberCommandHandler {

    private final MemberRegisterService memberRegisterService;

    @Transactional(rollbackFor = Exception.class)
    public Long register(MemberRegisterCommand command) {
        return memberRegisterService.register(command);
    }
} 