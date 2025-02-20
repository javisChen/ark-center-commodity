package com.ark.center.member.infra.member.repository.db.mapper;

import com.ark.center.member.infra.member.MemberLevelConfig;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberLevelMapper extends BaseMapper<MemberLevelConfig> {
    
} 