package com.ark.center.member.infra.member.dao.mapper;

import com.ark.center.member.infra.member.dao.entity.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

public interface MemberMapper extends BaseMapper<Member> {
    
    Integer countByMobile(@Param("mobile") String mobile);
} 