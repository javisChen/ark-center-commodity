package com.ark.center.member.infra.member.service.register;

import com.ark.center.member.client.member.command.MemberRegisterCommand;
import com.ark.center.member.client.member.common.IdentityType;
import com.ark.center.member.client.member.common.RegisterType;
import com.ark.center.member.infra.member.dao.entity.Member;
import com.ark.center.member.infra.member.dao.entity.MemberAuth;
import com.ark.center.member.infra.member.service.MemberAuthService;
import com.ark.center.member.infra.member.service.MemberService;
import com.ark.component.security.base.password.PasswordService;
import org.springframework.stereotype.Component;

@Component
public class UsernameRegisterStrategy extends AbstractRegisterStrategy {

    public UsernameRegisterStrategy(MemberService memberService, 
            MemberAuthService memberAuthService,
            PasswordService passwordService) {
        super(memberService, memberAuthService, passwordService);
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

    @Override
    protected void doRegisterMember(MemberRegisterCommand command, Member member) {
        // 不需要设置额外的会员信息，使用父类生成的默认昵称
    }
    
    @Override
    protected void doCreateMemberAuth(MemberRegisterCommand command, MemberAuth auth) {
        auth.setIdentityType(getIdentityType());
        auth.setIdentifier(getIdentifier(command));
    }
} 