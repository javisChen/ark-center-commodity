package com.ark.center.member.infra.member.service.register;

import com.ark.center.member.client.member.command.MemberRegisterCommand;
import com.ark.center.member.client.member.common.RegisterType;
import com.ark.center.member.infra.member.dao.entity.Member;

public interface RegisterStrategy {
    
    /**
     * 验证注册参数
     */
    void validate(MemberRegisterCommand command);
    
    /**
     * 执行注册
     */
    Member doRegister(MemberRegisterCommand command);
    
    /**
     * 获取支持的注册类型
     */
    RegisterType getRegisterType();
} 