package com.ark.center.member.app.member;

import com.ark.center.member.app.member.service.MemberRegisterService;
import com.ark.center.member.client.member.command.MemberRegisterCommand;
import com.ark.center.member.client.member.dto.MemberRegisterDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MemberCommandHandler {

    private final MemberRegisterService memberRegisterService;

    @Transactional(rollbackFor = Exception.class)
    public MemberRegisterDTO register(MemberRegisterCommand command) {
        return memberRegisterService.register(command);
    }
} 