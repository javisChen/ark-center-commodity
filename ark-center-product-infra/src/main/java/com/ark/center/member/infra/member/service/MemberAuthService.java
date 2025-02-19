package com.ark.center.member.infra.member.service;

import com.ark.center.member.client.member.common.IdentityType;
import com.ark.center.member.infra.member.dao.entity.MemberAuth;
import com.ark.center.member.infra.member.dao.mapper.MemberAuthMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class MemberAuthService extends ServiceImpl<MemberAuthMapper, MemberAuth> {
    
    public boolean isIdentifierExists(IdentityType identityType, String identifier) {
        return lambdaQuery()
                .eq(MemberAuth::getIdentityType, identityType)
                .eq(MemberAuth::getIdentifier, identifier)
                .exists();
    }
} 