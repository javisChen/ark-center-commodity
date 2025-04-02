package com.ark.center.member.adapter.member.web;

import com.ark.center.member.client.member.MemberCommandApi;
import com.ark.center.member.client.member.MemberQueryApi;
import com.ark.center.member.client.member.command.MemberRegisterCommand;
import com.ark.center.member.client.member.common.RegisterType;
import com.ark.center.member.client.member.dto.MemberAuthDTO;
import com.ark.center.member.client.member.dto.MemberRegisterDTO;
import com.ark.center.member.app.member.MemberCommandHandler;
import com.ark.center.member.infra.member.service.MemberAuthService;
import com.ark.component.dto.SingleResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/members")
@Tag(name = "会员管理接口")
public class MemberController implements MemberCommandApi, MemberQueryApi {
    
    private final MemberCommandHandler memberCommandHandler;
    private final MemberAuthService memberAuthService;
    
    @Override
    public SingleResponse<MemberRegisterDTO> register(@RequestBody @Validated MemberRegisterCommand command) {
        MemberRegisterDTO registerDTO = memberCommandHandler.register(command);
        return SingleResponse.ok(registerDTO);
    }
    
    @Override
    public SingleResponse<MemberAuthDTO> getMemberAuthInfo(String identity) {
        MemberAuthDTO memberAuthDTO = memberAuthService.getMemberAuthByIdentity(identity);
        return SingleResponse.ok(memberAuthDTO);
    }
    
    @Override
    public SingleResponse<MemberAuthDTO> getOrRegisterByMobile(String mobile) {
        log.info("开始获取或注册会员: mobile={}", mobile);
        
        // 先尝试获取会员信息
        MemberAuthDTO memberAuthDTO = memberAuthService.getMemberAuthByIdentity(mobile);
        
        // 如果会员不存在，则自动注册
        if (memberAuthDTO == null) {
            log.info("会员不存在，准备自动注册: mobile={}", mobile);
            
            // 调用注册接口
            MemberRegisterDTO registerDTO = registerByMobile(mobile);
            log.info("会员自动注册成功: mobile={}, memberId={}", mobile, registerDTO.getMemberId());
            
            // 获取新注册会员的认证信息
            memberAuthDTO = memberAuthService.getMemberAuthByIdentity(mobile);
        } else {
            log.info("会员已存在: mobile={}, memberId={}", mobile, memberAuthDTO.getMemberId());
        }
        
        return SingleResponse.ok(memberAuthDTO);
    }
    
    /**
     * 通过手机号注册会员
     * 
     * @param mobile 手机号
     * @return 注册结果
     */
    private MemberRegisterDTO registerByMobile(String mobile) {
        // 创建注册命令
        MemberRegisterCommand command = new MemberRegisterCommand();
        command.setRegisterType(RegisterType.MOBILE);
        command.setMobile(mobile);
        command.setRegisterChannel("OFFICIAL"); // 使用官方渠道
        
        // 调用现有的注册方法
        return memberCommandHandler.register(command);
    }
} 