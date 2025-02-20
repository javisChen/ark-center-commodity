package com.ark.center.member.infra.member.service.register;

import com.ark.center.auth.client.verifycode.common.VerifyCodeScene;
import com.ark.center.auth.infra.verifycode.service.VerifyCodeService;
import com.ark.center.member.client.member.command.MemberRegisterCommand;
import com.ark.center.member.client.member.common.RegisterType;
import com.ark.center.member.client.member.common.IdentityType;
import com.ark.center.member.infra.member.service.MemberService;
import com.ark.center.member.infra.member.service.MemberAuthService;
import com.ark.center.member.infra.member.service.MemberLevelService;
import com.ark.center.member.infra.member.service.MemberLevelRecordService;
import com.ark.component.exception.BizException;
import com.ark.component.security.base.password.PasswordService;
import org.springframework.stereotype.Component;

@Component
public class MobileRegisterStrategy extends AbstractRegisterStrategy {

    private final VerifyCodeService verifyCodeService;

    public MobileRegisterStrategy(MemberService memberService, 
            MemberAuthService memberAuthService,
            PasswordService passwordService,
            MemberLevelService memberLevelService,
            MemberLevelRecordService memberLevelRecordService,
            VerifyCodeService verifyCodeService) {
        super(memberService, memberAuthService, passwordService, memberLevelService, memberLevelRecordService);
        this.verifyCodeService = verifyCodeService;
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
        if (!verifyCodeService.verify(command.getMobile(), command.getVerifyCode(), 
                command.getVerifyCodeId(), VerifyCodeScene.REGISTER)) {
            throw new BizException("验证码错误");
        }
    }
} 