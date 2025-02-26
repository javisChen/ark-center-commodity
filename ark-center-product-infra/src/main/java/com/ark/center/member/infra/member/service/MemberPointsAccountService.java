package com.ark.center.member.infra.member.service;

import com.ark.center.member.client.member.common.PointsAccountStatus;
import com.ark.center.member.client.member.common.PointsCalcType;
import com.ark.center.member.client.member.common.PointsRecordType;
import com.ark.center.member.infra.member.MemberPointsAccount;
import com.ark.center.member.infra.member.repository.db.mapper.MemberPointsAccountMapper;
import com.ark.component.exception.BizException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberPointsAccountService extends ServiceImpl<MemberPointsAccountMapper, MemberPointsAccount> {
    
    private final MemberPointsRecordService pointsRecordService;
    private final MemberPointsBalanceService pointsBalanceService;
    
    /**
     * 获取会员积分账户，不存在则创建
     */
    @Transactional(rollbackFor = Exception.class)
    public MemberPointsAccount getOrCreateAccount(Long memberId) {
        log.info("开始获取会员积分账户: memberId={}", memberId);
        
        MemberPointsAccount account = lambdaQuery()
                .eq(MemberPointsAccount::getMemberId, memberId)
                .one();
                
        if (account == null) {
            log.info("会员积分账户不存在，准备创建新账户: memberId={}", memberId);
            account = new MemberPointsAccount();
            account.setMemberId(memberId);
            account.setAvailablePoints(0L);
            account.setFrozenPoints(0L);
            account.setTotalPoints(0L);
            account.setUsedPoints(0L);
            account.setExpiredPoints(0L);
            account.setAccountStatus(PointsAccountStatus.NORMAL);
            save(account);
            log.info("成功创建会员积分账户: memberId={}, accountId={}", memberId, account.getId());
        } else {
            log.info("成功获取会员积分账户: memberId={}, accountId={}, availablePoints={}", 
                    memberId, account.getId(), account.getAvailablePoints());
        }
        
        return account;
    }
    
    /**
     * 增加积分
     */
    @Transactional(rollbackFor = Exception.class)
    public void addPoints(Long memberId, Long points, String sceneCode, String description,
                          String bizNo, String ruleCode, String ruleName,
                          PointsCalcType calcType, Integer calcValue, Long baseValue,
                          String bizId, String bizType, java.time.LocalDateTime expireTime) {
        log.info("开始增加积分: memberId={}, points={}, sceneCode={}, bizNo={}, bizId={}, bizType={}", 
                memberId, points, sceneCode, bizNo, bizId, bizType);
        
        if (points <= 0) {
            log.info("积分增加失败，积分值必须大于0: points={}", points);
            throw new BizException("积分必须大于0: " + points);
        }
        
        // 获取或创建账户
        MemberPointsAccount account = getOrCreateAccount(memberId);
        if (account.getAccountStatus() == PointsAccountStatus.FROZEN) {
            log.info("积分增加失败，账户已冻结: memberId={}, accountId={}", memberId, account.getId());
            throw new BizException("账户已冻结");
        }
        
        // 更新账户
        Long beforePoints = account.getAvailablePoints();
        account.setAvailablePoints(beforePoints + points);
        account.setTotalPoints(account.getTotalPoints() + points);
        updateById(account);
        log.info("账户积分已更新: memberId={}, beforePoints={}, afterPoints={}, totalPoints={}", 
                memberId, beforePoints, account.getAvailablePoints(), account.getTotalPoints());
        
        // 创建积分流水
        Long recordId = pointsRecordService.createRecord(memberId, points, beforePoints, 
                account.getAvailablePoints(), PointsRecordType.EARN, sceneCode, 
                description, bizNo, expireTime, ruleCode, ruleName, calcType, 
                calcValue, baseValue, bizId, bizType, null);
        log.info("已创建积分流水记录: recordId={}", recordId);
                
        // 创建积分余额
        pointsBalanceService.createBalance(memberId, recordId, points, expireTime);
        log.info("已创建积分余额记录: memberId={}, recordId={}, points={}, expireTime={}", 
                memberId, recordId, points, expireTime);
        
        log.info("成功增加积分: memberId={}, points={}, afterPoints={}", 
                memberId, points, account.getAvailablePoints());
    }
    
    /**
     * 消费积分
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean consumePoints(Long memberId, Long points, String sceneCode, String description, 
                               String bizNo, String bizId, String bizType) {
        log.info("开始消费积分: memberId={}, points={}, sceneCode={}, bizNo={}, bizId={}, bizType={}", 
                memberId, points, sceneCode, bizNo, bizId, bizType);
        
        if (points <= 0) {
            log.info("积分消费失败，积分值必须大于0: points={}", points);
            throw new BizException("积分必须大于0: " + points);
        }
        
        // 获取账户
        MemberPointsAccount account = getOrCreateAccount(memberId);
        
        // 检查账户状态
        if (account.getAccountStatus() == PointsAccountStatus.FROZEN) {
            log.info("积分消费失败，账户已冻结: memberId={}, accountId={}", memberId, account.getId());
            throw new BizException("账户已冻结");
        } else if (account.getAccountStatus() == PointsAccountStatus.NORMAL) {
            log.info("账户状态正常，继续处理: memberId={}, accountId={}", memberId, account.getId());
        } else {
            log.info("积分消费失败，未知的账户状态: memberId={}, accountStatus={}", 
                    memberId, account.getAccountStatus());
            throw new BizException("未知的账户状态: " + account.getAccountStatus());
        }
        
        // 检查积分是否足够
        if (account.getAvailablePoints() < points) {
            log.info("积分消费失败，可用积分不足: memberId={}, availablePoints={}, requiredPoints={}", 
                    memberId, account.getAvailablePoints(), points);
            return false;
        }
        
        // 消费积分余额
        return pointsBalanceService.consumePoints(memberId, points, sourceRecordIds -> {
            // 更新账户
            Long beforePoints = account.getAvailablePoints();
            account.setAvailablePoints(beforePoints - points);
            account.setUsedPoints(account.getUsedPoints() + points);
            updateById(account);
            log.info("账户积分已更新: memberId={}, beforePoints={}, afterPoints={}, usedPoints={}", 
                    memberId, beforePoints, account.getAvailablePoints(), account.getUsedPoints());
            
            // 创建积分流水记录 - 使用第一个sourceRecordId或为null
            Long sourceRecordId = null;
            if (sourceRecordIds != null && !sourceRecordIds.isEmpty()) {
                sourceRecordId = sourceRecordIds.getFirst();
            }
            
            Long recordId = pointsRecordService.createRecord(memberId, -points, beforePoints, 
                    account.getAvailablePoints(), PointsRecordType.CONSUME, sceneCode, 
                    description, bizNo, null, null, null, null, 
                    null, null, bizId, bizType, sourceRecordId);
            log.info("已创建积分流水记录: recordId={}", recordId);
        });
    }
    
    /**
     * 冻结积分
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean freezePoints(Long memberId, Long points, String sceneCode, String description, 
                              String bizNo, String bizId, String bizType) {
        log.info("开始冻结积分: memberId={}, points={}, sceneCode={}, bizNo={}, bizId={}, bizType={}", 
                memberId, points, sceneCode, bizNo, bizId, bizType);
        
        if (points <= 0) {
            log.info("积分冻结失败，积分值必须大于0: points={}", points);
            throw new BizException("积分必须大于0: " + points);
        }
        
        // 获取账户
        MemberPointsAccount account = getOrCreateAccount(memberId);
        if (account.getAccountStatus() == PointsAccountStatus.FROZEN) {
            log.info("积分冻结失败，账户已冻结: memberId={}, accountId={}", memberId, account.getId());
            throw new BizException("账户已冻结");
        }
        
        // 检查积分是否足够
        if (account.getAvailablePoints() < points) {
            log.info("积分冻结失败，可用积分不足: memberId={}, availablePoints={}, requiredPoints={}", 
                    memberId, account.getAvailablePoints(), points);
            return false;
        }
        
        // 更新账户
        Long beforePoints = account.getAvailablePoints();
        account.setAvailablePoints(beforePoints - points);
        account.setFrozenPoints(account.getFrozenPoints() + points);
        updateById(account);
        log.info("账户积分已更新: memberId={}, beforePoints={}, afterPoints={}, frozenPoints={}", 
                memberId, beforePoints, account.getAvailablePoints(), account.getFrozenPoints());
        
        // 创建积分流水
        Long recordId = pointsRecordService.createRecord(memberId, -points, beforePoints, 
                account.getAvailablePoints(), PointsRecordType.FREEZE, sceneCode, 
                description, bizNo, null, null, null, null, 
                null, null, bizId, bizType, null);
        log.info("已创建积分流水记录: recordId={}", recordId);
                
        log.info("成功冻结积分: memberId={}, points={}, frozenPoints={}", 
                memberId, points, account.getFrozenPoints());
        return true;
    }
    
    /**
     * 解冻积分
     */
    @Transactional(rollbackFor = Exception.class)
    public void unfreezePoints(Long memberId, Long points, String sceneCode, String description, 
                             String bizNo, String bizId, String bizType) {
        log.info("开始解冻积分: memberId={}, points={}, sceneCode={}, bizNo={}, bizId={}, bizType={}", 
                memberId, points, sceneCode, bizNo, bizId, bizType);
        
        if (points <= 0) {
            log.info("积分解冻失败，积分值必须大于0: points={}", points);
            throw new BizException("积分必须大于0: " + points);
        }
        
        // 获取账户
        MemberPointsAccount account = getOrCreateAccount(memberId);
        
        // 检查冻结积分是否足够
        if (account.getFrozenPoints() < points) {
            log.info("积分解冻失败，冻结积分不足: memberId={}, frozenPoints={}, requiredPoints={}", 
                    memberId, account.getFrozenPoints(), points);
            throw new BizException("解冻积分超过冻结积分: 冻结积分=" + account.getFrozenPoints() + 
                    ", 请求解冻=" + points);
        }
        
        // 更新账户
        Long beforePoints = account.getAvailablePoints();
        account.setAvailablePoints(beforePoints + points);
        account.setFrozenPoints(account.getFrozenPoints() - points);
        updateById(account);
        log.info("账户积分已更新: memberId={}, beforePoints={}, afterPoints={}, frozenPoints={}", 
                memberId, beforePoints, account.getAvailablePoints(), account.getFrozenPoints());
        
        // 创建积分流水
        Long recordId = pointsRecordService.createRecord(memberId, points, beforePoints, 
                account.getAvailablePoints(), PointsRecordType.UNFREEZE, sceneCode, 
                description, bizNo, null, null, null, null, 
                null, null, bizId, bizType, null);
        log.info("已创建积分流水记录: recordId={}", recordId);
        
        log.info("成功解冻积分: memberId={}, points={}, afterPoints={}, frozenPoints={}", 
                memberId, points, account.getAvailablePoints(), account.getFrozenPoints());
    }
    
    /**
     * 处理积分过期
     */
    @Transactional(rollbackFor = Exception.class)
    public void expirePoints(Long memberId, Long points, Long balanceId) {
        log.info("开始处理积分过期: memberId={}, points={}, balanceId={}", memberId, points, balanceId);
        
        if (points <= 0) {
            log.info("积分过期处理失败，积分值必须大于0: points={}", points);
            throw new BizException("积分必须大于0: " + points);
        }
        
        // 获取账户
        MemberPointsAccount account = getOrCreateAccount(memberId);
        
        // 检查可用积分是否足够
        if (account.getAvailablePoints() < points) {
            log.info("积分过期处理调整，可用积分不足: memberId={}, availablePoints={}, requestedPoints={}", 
                    memberId, account.getAvailablePoints(), points);
            points = account.getAvailablePoints(); // 如果可用积分不足，以可用积分为准
            log.info("已调整过期积分值为可用积分: adjustedPoints={}", points);
        }
        
        // 更新账户
        Long beforePoints = account.getAvailablePoints();
        account.setAvailablePoints(beforePoints - points);
        account.setExpiredPoints(account.getExpiredPoints() + points);
        updateById(account);
        log.info("账户积分已更新: memberId={}, beforePoints={}, afterPoints={}, expiredPoints={}", 
                memberId, beforePoints, account.getAvailablePoints(), account.getExpiredPoints());
        
        // 创建积分流水
        Long recordId = pointsRecordService.createRecord(memberId, -points, beforePoints, 
                account.getAvailablePoints(), PointsRecordType.EXPIRE, "POINTS_EXPIRE", 
                "积分过期", null, null, null, null, null, 
                null, null, balanceId.toString(), "BALANCE", null);
        log.info("已创建积分流水记录: recordId={}", recordId);
        
        log.info("成功处理积分过期: memberId={}, points={}, afterPoints={}, expiredPoints={}", 
                memberId, points, account.getAvailablePoints(), account.getExpiredPoints());
    }
} 