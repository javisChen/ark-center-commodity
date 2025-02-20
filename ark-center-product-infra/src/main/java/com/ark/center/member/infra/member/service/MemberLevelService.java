package com.ark.center.member.infra.member.service;

import com.ark.center.member.infra.member.Member;
import com.ark.center.member.infra.member.MemberLevelConfig;
import com.ark.center.member.infra.member.repository.db.mapper.MemberLevelMapper;
import com.ark.component.exception.BizException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberLevelService extends ServiceImpl<MemberLevelMapper, MemberLevelConfig> {
    
    private final MemberService memberService;
    
    /**
     * 获取默认等级
     */
    public MemberLevelConfig getDefaultLevel() {
        return lambdaQuery()
                .eq(MemberLevelConfig::getIsDefault, true)
                .eq(MemberLevelConfig::getStatus, true)
                .one();
    }
    
    /**
     * 根据成长值计算等级
     */
    public MemberLevelConfig calculateLevel(Long growthValue) {
        return lambdaQuery()
                .eq(MemberLevelConfig::getStatus, true)
                .le(MemberLevelConfig::getGrowthValue, growthValue)
                .orderByDesc(MemberLevelConfig::getLevel)
                .last("LIMIT 1")
                .one();
    }
    
    /**
     * 增加成长值并更新等级
     */
    @Transactional(rollbackFor = Exception.class)
    public void addGrowthValue(Long memberId, Long growthValue) {
        Member member = memberService.getById(memberId);
        if (member == null) {
            throw new BizException("会员不存在");
        }
        
        // 计算新的成长值
        Long newGrowthValue = member.getGrowthValue() + growthValue;
        
        // 计算新的等级
        MemberLevelConfig newLevel = calculateLevel(newGrowthValue);
        if (newLevel == null) {
            throw new BizException("等级配置异常");
        }
        
        // 更新会员信息
        member.setGrowthValue(newGrowthValue);
        member.setLevel(newLevel.getLevel());
        memberService.updateById(member);
    }
} 