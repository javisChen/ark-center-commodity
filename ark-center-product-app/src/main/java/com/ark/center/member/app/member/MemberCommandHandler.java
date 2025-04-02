package com.ark.center.member.app.member;

import com.ark.center.member.app.member.service.MemberRegisterService;
import com.ark.center.member.client.member.command.MemberRegisterCommand;
import com.ark.center.member.client.member.dto.MemberRegisterDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberCommandHandler {

    private final MemberRegisterService memberRegisterService;

    @Transactional(rollbackFor = Exception.class)
    public MemberRegisterDTO register(MemberRegisterCommand command) {
        log.info("处理会员注册请求: registerType={}, mobile={}", command.getRegisterType(), command.getMobile());
        MemberRegisterDTO result = memberRegisterService.register(command);
        log.info("会员注册成功: memberId={}, memberNo={}", result.getMemberId(), result.getMemberNo());
        return result;
    }
} 