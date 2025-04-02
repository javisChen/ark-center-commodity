package com.ark.center.member.infra.member.service.register;

import com.ark.center.member.client.member.command.MemberRegisterCommand;
import com.ark.center.member.client.member.common.IdentityType;
import com.ark.center.member.client.member.common.RegisterType;
import com.ark.center.member.infra.channel.service.RegisterChannelService;
import com.ark.center.member.infra.member.service.MemberAuthService;
import com.ark.center.member.infra.member.service.MemberLevelRecordService;
import com.ark.center.member.infra.member.service.MemberLevelService;
import com.ark.center.member.infra.member.service.MemberService;
import com.ark.component.security.base.password.PasswordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MobileRegisterStrategy extends AbstractRegisterStrategy {

    public MobileRegisterStrategy(MemberService memberService, 
            MemberAuthService memberAuthService,
            PasswordService passwordService,
            MemberLevelService memberLevelService,
            MemberLevelRecordService memberLevelRecordService,
            RegisterChannelService registerChannelService) {
        super(memberService, memberAuthService, passwordService, memberLevelService, memberLevelRecordService, registerChannelService);
    }

    @Override
    public RegisterType getRegisterType() {
        return RegisterType.MOBILE;
    }

    @Override
    protected IdentityType getIdentityType() {
        return IdentityType.MOBILE;
    }

    @Override
    protected String getIdentifier(MemberRegisterCommand command) {
        return command.getMobile();
    }

    @Override
    protected void doValidate(MemberRegisterCommand command) {
        // todo 如果从认证中心进来的，无需校验验证码，验证码校验由认证中心处理
    }
} 