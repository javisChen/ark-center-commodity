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
import org.springframework.stereotype.Component;

@Component
public class UsernameRegisterStrategy extends AbstractRegisterStrategy {

    public UsernameRegisterStrategy(MemberService memberService,
                                    MemberAuthService memberAuthService,
                                    PasswordService passwordService,
                                    MemberLevelService memberLevelService,
                                    MemberLevelRecordService memberLevelRecordService,
                                    RegisterChannelService registerChannelService) {
        super(memberService,
                memberAuthService,
                passwordService,
                memberLevelService,
                memberLevelRecordService,
                registerChannelService);
    }

    @Override
    public RegisterType getRegisterType() {
        return RegisterType.USERNAME;
    }

    @Override
    protected IdentityType getIdentityType() {
        return IdentityType.USERNAME;
    }

    @Override
    protected String getIdentifier(MemberRegisterCommand command) {
        return command.getUsername();
    }

    @Override
    protected void doValidate(MemberRegisterCommand command) {
        // 用户名注册不需要额外的验证
    }
}