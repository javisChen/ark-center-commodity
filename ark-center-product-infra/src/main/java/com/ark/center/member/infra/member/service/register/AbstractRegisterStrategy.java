package com.ark.center.member.infra.member.service.register;

import com.ark.center.member.client.member.command.MemberRegisterCommand;
import com.ark.center.member.client.member.common.IdentityType;
import com.ark.center.member.client.member.common.MemberStatus;
import com.ark.center.member.infra.member.dao.entity.Member;
import com.ark.center.member.infra.member.dao.entity.MemberAuth;
import com.ark.center.member.infra.member.service.MemberAuthService;
import com.ark.center.member.infra.member.service.MemberService;
import com.ark.component.exception.BizException;
import com.ark.component.security.base.password.PasswordService;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public abstract class AbstractRegisterStrategy implements RegisterStrategy {

    protected final MemberService memberService;
    protected final MemberAuthService memberAuthService;
    protected final PasswordService passwordService;

    @Override
    public void validate(MemberRegisterCommand command) {
        // 1. 验证认证标识是否已存在
        if (memberAuthService.isIdentifierExists(getIdentityType(), getIdentifier(command))) {
            throw new BizException(getIdentityType().getDesc() + "已被注册");
        }
        
        // 2. 子类特定验证
        doValidate(command);
    }

    @Override
    public Member doRegister(MemberRegisterCommand command) {
        // 1. 创建会员基础信息
        Member member = new Member();
        // 设置基础属性
        member.setStatus(MemberStatus.ENABLE); // 默认启用
        member.setLevel(1);  // 默认等级
        member.setGrowthValue(0L);
        member.setPoints(0L);
        member.setNickname(generateDefaultNickname()); // 统一设置默认昵称
        
        // 子类特定注册逻辑
        doRegisterMember(command, member);
        
        // 保存会员信息
        memberService.save(member);
        
        // 2. 创建认证信息
        MemberAuth auth = new MemberAuth();
        auth.setMemberId(member.getId());
        auth.setCredential(passwordService.enhancePassword(command.getPassword()));
        
        // 子类设置认证信息
        doCreateMemberAuth(command, auth);
        
        // 保存认证信息
        memberAuthService.save(auth);
        
        return member;
    }

    /**
     * 获取认证类型
     */
    protected abstract IdentityType getIdentityType();

    /**
     * 获取认证标识
     */
    protected abstract String getIdentifier(MemberRegisterCommand command);

    /**
     * 子类实现具体的验证逻辑
     */
    protected abstract void doValidate(MemberRegisterCommand command);

    /**
     * 子类实现具体的注册会员逻辑
     */
    protected abstract void doRegisterMember(MemberRegisterCommand command, Member member);
    
    /**
     * 子类实现具体的认证信息创建逻辑
     */
    protected abstract void doCreateMemberAuth(MemberRegisterCommand command, MemberAuth auth);

    /**
     * 生成默认昵称：用户+12位随机字符串
     */
    protected String generateDefaultNickname() {
        return "用户" + UUID.randomUUID().toString().substring(0, 12);
    }
} 