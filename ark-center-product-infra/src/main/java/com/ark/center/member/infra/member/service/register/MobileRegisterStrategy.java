package com.ark.center.member.infra.member.service.register;

import com.ark.center.auth.client.verifycode.common.VerifyCodeScene;
import com.ark.center.auth.infra.verifycode.service.VerifyCodeService;
import com.ark.center.member.client.member.command.MemberRegisterCommand;
import com.ark.center.member.client.member.common.RegisterType;
import com.ark.center.member.client.member.common.IdentityType;
import com.ark.center.member.infra.member.dao.entity.Member;
import com.ark.center.member.infra.member.dao.entity.MemberAuth;
import com.ark.center.member.infra.member.service.MemberService;
import com.ark.center.member.infra.member.service.MemberAuthService;
import com.ark.component.exception.BizException;
import com.ark.component.security.base.password.PasswordService;
import org.springframework.stereotype.Component;

@Component
public class MobileRegisterStrategy extends AbstractRegisterStrategy {

    private final VerifyCodeService verifyCodeService;

    public MobileRegisterStrategy(MemberService memberService, 
            MemberAuthService memberAuthService,
            PasswordService passwordService,
            VerifyCodeService verifyCodeService) {
        super(memberService, memberAuthService, passwordService);
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
        // 只需要验证验证码
        if (!verifyCodeService.verify(command.getMobile(), command.getVerifyCode(), 
                command.getVerifyCodeId(), VerifyCodeScene.REGISTER)) {
            throw new BizException("验证码错误");
        }
    }

    @Override
    protected void doRegisterMember(MemberRegisterCommand command, Member member) {
        member.setMobile(command.getMobile());
    }
    
    @Override
    protected void doCreateMemberAuth(MemberRegisterCommand command, MemberAuth auth) {
        auth.setIdentityType(getIdentityType());
        auth.setIdentifier(getIdentifier(command));
    }
} 