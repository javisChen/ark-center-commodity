package com.ark.center.member.infra.growth.service;

import com.ark.center.member.infra.growth.MemberGrowthRecord;
import com.ark.center.member.infra.growth.repository.db.mapper.MemberGrowthRecordMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberGrowthRecordService extends ServiceImpl<MemberGrowthRecordMapper, MemberGrowthRecord> {
    
} 