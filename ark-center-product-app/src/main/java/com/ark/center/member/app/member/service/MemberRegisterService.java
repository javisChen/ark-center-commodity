package com.ark.center.member.app.member.service;

import com.ark.center.member.client.member.command.MemberRegisterCommand;
import com.ark.center.member.client.member.common.RegisterType;
import com.ark.center.member.client.member.dto.MemberRegisterDTO;
import com.ark.center.member.infra.member.Member;
import com.ark.center.member.infra.member.service.MemberService;
import com.ark.center.member.infra.member.service.register.RegisterStrategy;
import com.ark.component.exception.BizException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberRegisterService {
    
    private final MemberService memberService;
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
    public MemberRegisterDTO register(MemberRegisterCommand command) {
        RegisterStrategy strategy = registerStrategyMap.get(command.getRegisterType());
        if (strategy == null) {
            throw new BizException("不支持的注册类型");
        }
        
        // 验证参数
        strategy.validate(command);
        
        // 执行注册
        Member member = strategy.doRegister(command);
        
        // 返回注册结果
        MemberRegisterDTO registerDTO = new MemberRegisterDTO();
        registerDTO.setMemberId(member.getId());
        registerDTO.setMemberNo(member.getMemberNo());
        return registerDTO;
    }
} 