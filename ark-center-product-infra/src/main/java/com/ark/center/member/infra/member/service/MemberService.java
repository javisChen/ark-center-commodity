package com.ark.center.member.infra.member.service;

import com.ark.center.member.client.member.command.MemberRegisterCommand;
import com.ark.center.member.client.member.common.RegisterType;
import com.ark.center.member.infra.member.dao.entity.Member;
import com.ark.center.member.infra.member.dao.mapper.MemberAuthMapper;
import com.ark.center.member.infra.member.dao.mapper.MemberMapper;
import com.ark.center.member.infra.member.service.register.RegisterStrategy;
import com.ark.component.exception.BizException;
import com.ark.component.security.base.password.PasswordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberService extends ServiceImpl<MemberMapper, Member> {
    
    private final MemberAuthMapper memberAuthMapper;
    private final PasswordService passwordService;
    private final ApplicationContext applicationContext;
    private Map<RegisterType, RegisterStrategy> registerStrategyMap;
    
    @PostConstruct
    public void init() {
        registerStrategyMap = new HashMap<>();
        applicationContext.getBeansOfType(RegisterStrategy.class)
            .values()
            .forEach(strategy -> registerStrategyMap.put(strategy.getRegisterType(), strategy));
    }
    
    @Transactional(rollbackFor = Exception.class)
    public Long register(MemberRegisterCommand command) {
        RegisterStrategy strategy = registerStrategyMap.get(command.getRegisterType());
        if (strategy == null) {
            throw new BizException("不支持的注册类型");
        }
        
        // 1. 验证参数
        strategy.validate(command);
        
        // 2. 执行注册
        Member member = strategy.doRegister(command);
        
        return member.getId();
    }

    // 基础的 CRUD 方法，使用 lambda 查询
    public Member getByUsername(String username) {
        return lambdaQuery()
                .eq(Member::getUsername, username)
                .one();
    }
    
    public Member getByPhone(String phone) {
        return lambdaQuery()
                .eq(Member::getPhone, phone)
                .one();
    }
    
    public Member getByEmail(String email) {
        return lambdaQuery()
                .eq(Member::getEmail, email)
                .one();
    }
}