package com.ark.center.member.infra.member.service;

import com.ark.center.member.infra.member.MemberPointsBalance;
import com.ark.center.member.infra.member.repository.db.mapper.MemberPointsBalanceMapper;
import com.ark.component.exception.BizException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberPointsBalanceService extends ServiceImpl<MemberPointsBalanceMapper, MemberPointsBalance> {
    
    /**
     * 创建积分余额
     */
    @Transactional(rollbackFor = Exception.class)
    public void createBalance(Long memberId, Long sourceRecordId, Long points, LocalDateTime expireTime) {
        log.info("开始创建积分余额: memberId={}, sourceRecordId={}, points={}, expireTime={}", 
                memberId, sourceRecordId, points, expireTime);
        
        MemberPointsBalance balance = new MemberPointsBalance();
        balance.setMemberId(memberId);
        balance.setSourceRecordId(sourceRecordId);
        balance.setTotalPoints(points);
        balance.setAvailablePoints(points);
        balance.setUsedPoints(0L);
        balance.setExpireTime(expireTime);
        
        save(balance);
        log.info("成功创建积分余额: balanceId={}, memberId={}, points={}, expireTime={}", 
                balance.getId(), memberId, points, expireTime);
    }
    
    /**
     * 消费积分
     * 基于先过期先消费策略
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean consumePoints(Long memberId, Long points, Consumer<List<Long>> callback) {
        log.info("开始消费积分: memberId={}, points={}", memberId, points);
        
        if (points <= 0) {
            log.info("积分消费失败，积分值必须大于0: points={}", points);
            throw new BizException("积分必须大于0: " + points);
        }
        
        // 获取所有可用积分余额，按过期时间升序
        List<MemberPointsBalance> balances = lambdaQuery()
                .eq(MemberPointsBalance::getMemberId, memberId)
                .gt(MemberPointsBalance::getAvailablePoints, 0)
                .orderByAsc(MemberPointsBalance::getExpireTime)
                .list();
        log.info("获取到积分余额记录: memberId={}, balanceCount={}", memberId, balances.size());
        
        // 计算总可用积分
        long totalAvailable = balances.stream()
                .mapToLong(MemberPointsBalance::getAvailablePoints)
                .sum();
        log.info("会员总可用积分: memberId={}, totalAvailable={}, requiredPoints={}", 
                memberId, totalAvailable, points);
                
        if (totalAvailable < points) {
            log.info("积分消费失败，可用积分不足: memberId={}, totalAvailable={}, requiredPoints={}", 
                    memberId, totalAvailable, points);
            return false; // 积分不足
        }
        
        // 消费积分
        long remaining = points;
        List<Long> sourceRecordIds = new ArrayList<>();
        
        for (MemberPointsBalance balance : balances) {
            if (remaining <= 0) break;
            
            long pointsToDeduct = Math.min(balance.getAvailablePoints(), remaining);
            log.info("从余额中扣减积分: balanceId={}, expireTime={}, deduct={}, available={}", 
                    balance.getId(), balance.getExpireTime(), pointsToDeduct, balance.getAvailablePoints());
            
            balance.setAvailablePoints(balance.getAvailablePoints() - pointsToDeduct);
            balance.setUsedPoints(balance.getUsedPoints() + pointsToDeduct);
            updateById(balance);
            log.info("余额更新完成: balanceId={}, remaining={}, used={}", 
                    balance.getId(), balance.getAvailablePoints(), balance.getUsedPoints());
            
            sourceRecordIds.add(balance.getSourceRecordId());
            remaining -= pointsToDeduct;
        }
        
        log.info("已从{}个余额中消费积分，记录IDs: {}", sourceRecordIds.size(), sourceRecordIds);
        
        // 执行回调
        log.info("开始执行积分消费回调");
        callback.accept(sourceRecordIds);
        log.info("积分消费回调执行完成");
        
        log.info("积分消费成功: memberId={}, points={}, remainingToDeduct={}", 
                memberId, points, remaining);
        return true;
    }
    
    /**
     * 查询即将过期的积分
     */
    public List<MemberPointsBalance> getExpiringBalances(LocalDateTime expiryTime) {
        log.info("开始查询即将过期的积分: expiryTime={}", expiryTime);
        
        List<MemberPointsBalance> balances = lambdaQuery()
                .lt(MemberPointsBalance::getExpireTime, expiryTime)
                .gt(MemberPointsBalance::getAvailablePoints, 0)
                .list();
        
        log.info("查询到{}条即将过期的积分记录", balances.size());
        return balances;
    }
    
    /**
     * 获取会员积分余额明细
     */
    public List<MemberPointsBalance> getMemberBalances(Long memberId) {
        log.info("开始获取会员积分余额明细: memberId={}", memberId);
        
        List<MemberPointsBalance> balances = lambdaQuery()
                .eq(MemberPointsBalance::getMemberId, memberId)
                .gt(MemberPointsBalance::getAvailablePoints, 0)
                .orderByAsc(MemberPointsBalance::getExpireTime)
                .list();
        
        log.info("获取到会员积分余额明细: memberId={}, balanceCount={}", memberId, balances.size());
        return balances;
    }
} 