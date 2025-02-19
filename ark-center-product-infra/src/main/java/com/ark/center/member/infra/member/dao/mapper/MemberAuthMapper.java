package com.ark.center.member.infra.member.dao.mapper;

import com.ark.center.member.infra.member.dao.entity.MemberAuth;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

public interface MemberAuthMapper extends BaseMapper<MemberAuth> {
    
    Integer countByIdentifier(@Param("identityType") IdentityTypeEnum identityType, @Param("identifier") String identifier);
} 