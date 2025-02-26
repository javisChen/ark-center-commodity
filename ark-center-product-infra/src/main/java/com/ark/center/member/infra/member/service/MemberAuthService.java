package com.ark.center.member.infra.member.service;

import com.ark.center.member.client.member.common.IdentityType;
import com.ark.center.member.client.member.dto.MemberAuthDTO;
import com.ark.center.member.infra.member.Member;
import com.ark.center.member.infra.member.MemberAuth;
import com.ark.center.member.infra.member.repository.db.mapper.MemberAuthMapper;
import com.ark.center.member.infra.member.repository.db.mapper.MemberMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MemberAuthService extends ServiceImpl<MemberAuthMapper, MemberAuth> {
    
    private static final Logger log = LoggerFactory.getLogger(MemberAuthService.class);
    
    private final MemberMapper memberMapper;
    private final MemberService memberService;
    
    public MemberAuthService(MemberMapper memberMapper, MemberService memberService) {
        this.memberMapper = memberMapper;
        this.memberService = memberService;
    }
    
    /**
     * 检查认证标识是否存在
     */
    public boolean isIdentifierExists(IdentityType identityType, String identifier) {
        return lambdaQuery()
                .eq(MemberAuth::getIdentityType, identityType)
                .eq(MemberAuth::getIdentifier, identifier)
                .exists();
    }

    /**
     * 根据认证类型和标识查询会员认证信息
     * 
     * @param identityType 认证类型
     * @param identifier 认证标识
     * @return 认证信息
     */
    public MemberAuth getByIdentityTypeAndIdentifier(IdentityType identityType, String identifier) {
        return lambdaQuery()
                .eq(MemberAuth::getIdentityType, identityType)
                .eq(MemberAuth::getIdentifier, identifier)
                .one();
    }
    
    /**
     * 根据用户名或手机号查询会员认证信息
     * 
     * @param identity 用户名或手机号
     * @return 会员认证信息DTO
     */
    public MemberAuthDTO getMemberAuthByIdentity(String identity) {
        log.info("开始查询会员认证信息: identity={}", identity);
        
        // 优先尝试作为手机号查询
        MemberAuth auth = getByIdentityTypeAndIdentifier(IdentityType.MOBILE, identity);
        
        // 如果未找到，尝试作为用户名查询
        if (auth == null) {
            auth = getByIdentityTypeAndIdentifier(IdentityType.USERNAME, identity);
            if (auth != null) {
                log.info("通过用户名查询会员成功: identity={}", identity);
            }
        } else {
            log.info("通过手机号查询会员成功: identity={}", identity);
        }
        
        if (auth == null) {
            log.info("未找到会员认证信息: identity={}", identity);
            return null;
        }
        
        return buildMemberAuthDTO(auth, identity);
    }
    
    /**
     * 根据认证信息构建会员认证DTO
     */
    private MemberAuthDTO buildMemberAuthDTO(MemberAuth auth, String identity) {
        // 查询会员基本信息
        Member member = memberService.getById(auth.getMemberId());
        if (member == null) {
            log.warn("会员认证记录存在但会员基本信息不存在: memberId={}, identity={}", 
                    auth.getMemberId(), identity);
            return null;
        }
        
        // 构建返回结果
        MemberAuthDTO result = new MemberAuthDTO();
        result.setMemberId(member.getId());
        result.setMobile(member.getMobile());
        result.setMemberStatus(member.getStatus());
        
        // 设置认证相关信息
        result.setPassword(auth.getCredential());
        result.setIdentityType(auth.getIdentityType());
        result.setIdentifier(auth.getIdentifier());
        
        log.info("会员认证信息查询成功: memberId={}", member.getId());
        return result;
    }
} 