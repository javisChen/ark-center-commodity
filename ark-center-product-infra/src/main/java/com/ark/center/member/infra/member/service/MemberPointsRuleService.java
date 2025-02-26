package com.ark.center.member.infra.member.service;

import com.ark.center.member.client.member.common.PointsCalcType;
import com.ark.center.member.client.member.common.PointsRuleType;
import com.ark.center.member.infra.member.MemberPointsRule;
import com.ark.center.member.infra.member.repository.db.mapper.MemberPointsRuleMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberPointsRuleService extends ServiceImpl<MemberPointsRuleMapper, MemberPointsRule> {
    
    private final MemberPointsRecordService pointsRecordService;
    
    /**
     * 根据场景获取积分规则
     */
    public MemberPointsRule getRuleByScene(String sceneCode, PointsRuleType ruleType) {
        return lambdaQuery()
                .eq(MemberPointsRule::getSceneCode, sceneCode)
                .eq(MemberPointsRule::getRuleType, ruleType)
                .eq(MemberPointsRule::getStatus, true)
                .one();
    }
    
    /**
     * 计算积分值
     */
    public Long calculatePoints(MemberPointsRule rule, Long amount) {
        if (rule == null) {
            return 0L;
        }
        
        if (rule.getCalcType() == PointsCalcType.FIXED) {
            return Long.valueOf(rule.getPointsValue());
        } else if (rule.getCalcType() == PointsCalcType.RATE) {
            return amount * rule.getPointsValue() / 100; // 按比例计算
        }
        
        return 0L;
    }
    
    /**
     * 检查周期限制
     */
    public boolean checkPeriodLimit(Long memberId, String sceneCode, Long points) {
        MemberPointsRule rule = getRuleByScene(sceneCode, PointsRuleType.EARN);
        if (rule == null || rule.getPeriodType() == 0 || rule.getPeriodLimit() == 0) {
            return true; // 没有规则或无周期限制
        }
        
        // 获取当前周期内已获得的积分
        Long periodPoints = pointsRecordService.getPeriodPoints(memberId, sceneCode, rule.getPeriodType());
        
        // 检查是否超过限制
        return periodPoints + points <= rule.getPeriodLimit();
    }
} 