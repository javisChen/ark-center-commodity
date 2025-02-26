package com.ark.center.member.infra.member.service;

import com.ark.center.member.client.member.common.PointsCalcType;
import com.ark.center.member.client.member.common.PointsRecordType;
import com.ark.center.member.infra.member.MemberPointsRecord;
import com.ark.center.member.infra.member.MemberPointsRecordDetail;
import com.ark.center.member.infra.member.repository.db.mapper.MemberPointsRecordMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberPointsRecordService extends ServiceImpl<MemberPointsRecordMapper, MemberPointsRecord> {
    
    private final MemberPointsRecordDetailService detailService;
    
    /**
     * 创建积分流水记录（包含明细）
     */
    @Transactional(rollbackFor = Exception.class)
    public Long createRecord(Long memberId, Long points, Long beforePoints, Long afterPoints,
                          PointsRecordType recordType, String sceneCode, String description,
                          String bizNo, LocalDateTime expireTime, String ruleCode, String ruleName,
                          PointsCalcType calcType, Integer calcValue, Long baseValue,
                          String bizId, String bizType, Long sourceRecordId) {
        log.info("开始创建积分流水记录: memberId={}, points={}, recordType={}, sceneCode={}, bizNo={}, bizId={}, bizType={}", 
                memberId, points, recordType, sceneCode, bizNo, bizId, bizType);
        
        // 1. 创建主流水记录
        MemberPointsRecord record = new MemberPointsRecord();
        record.setMemberId(memberId);
        record.setPoints(points);
        record.setBeforePoints(beforePoints);
        record.setAfterPoints(afterPoints);
        record.setRecordType(recordType);
        record.setSceneCode(sceneCode);
        record.setDescription(description);
        record.setBizNo(bizNo);
        record.setExpireTime(expireTime);
        
        save(record);
        log.info("积分流水主记录创建成功: recordId={}, points={}, recordType={}", 
                record.getId(), points, recordType);
        
        // 2. 创建明细记录
        if (ruleCode != null || bizId != null) {
            log.info("开始创建积分流水明细: recordId={}, ruleCode={}, bizId={}", 
                    record.getId(), ruleCode, bizId);
            MemberPointsRecordDetail detail = new MemberPointsRecordDetail();
            detail.setRecordId(record.getId());
            detail.setMemberId(memberId);
            detail.setRuleCode(ruleCode);
            detail.setRuleName(ruleName);
            detail.setCalcType(calcType);
            detail.setCalcValue(calcValue);
            detail.setBaseValue(baseValue);
            detail.setBizId(bizId);
            detail.setBizType(bizType);
            detail.setExtraInfo(sourceRecordId != null ? "sourceRecordId=" + sourceRecordId : null);
            
            detailService.save(detail);
            log.info("积分流水明细创建成功: detailId={}, recordId={}", detail.getId(), record.getId());
        } else {
            log.info("无需创建积分流水明细记录: recordId={}", record.getId());
        }
        
        log.info("积分流水记录创建完成: recordId={}, points={}, recordType={}", 
                record.getId(), points, recordType);
        return record.getId();
    }
    
    /**
     * 获取周期内获得的积分总和
     */
    public Long getPeriodPoints(Long memberId, String sceneCode, Integer periodType) {
        log.info("开始查询周期内获得的积分: memberId={}, sceneCode={}, periodType={}", 
                memberId, sceneCode, periodType);
        
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startTime;
        LocalDateTime endTime = now;
        
        // 确定周期的开始时间
        switch (periodType) {
            case 1: // 每天
                startTime = LocalDateTime.of(now.toLocalDate(), LocalTime.MIN);
                break;
            case 2: // 每周
                startTime = LocalDateTime.of(now.toLocalDate().minusDays(now.getDayOfWeek().getValue() - 1), LocalTime.MIN);
                break;
            case 3: // 每月
                startTime = LocalDateTime.of(now.toLocalDate().withDayOfMonth(1), LocalTime.MIN);
                break;
            case 4: // 每年
                startTime = LocalDateTime.of(LocalDate.of(now.getYear(), 1, 1), LocalTime.MIN);
                break;
            default:
                log.info("未知的周期类型，默认返回0: periodType={}", periodType);
                return 0L;
        }
        
        log.info("周期查询范围: startTime={}, endTime={}, periodType={}", 
                startTime, endTime, periodType);
        
        // 查询积分总和
        Long totalPoints = lambdaQuery()
                .eq(MemberPointsRecord::getMemberId, memberId)
                .eq(MemberPointsRecord::getSceneCode, sceneCode)
                .eq(MemberPointsRecord::getRecordType, PointsRecordType.EARN)
                .ge(MemberPointsRecord::getGmtCreate, startTime)
                .le(MemberPointsRecord::getGmtCreate, endTime)
                .select(MemberPointsRecord::getPoints)
                .list()
                .stream()
                .mapToLong(MemberPointsRecord::getPoints)
                .sum();
        
        log.info("周期内获得的积分总和: memberId={}, sceneCode={}, periodType={}, totalPoints={}", 
                memberId, sceneCode, periodType, totalPoints);
        return totalPoints;
    }
    
    /**
     * 查询会员积分流水（分页）
     */
    public IPage<MemberPointsRecord> queryMemberPointsRecords(Long memberId, PointsRecordType recordType, 
                                                           LocalDateTime startTime, LocalDateTime endTime, 
                                                           Page<MemberPointsRecord> page) {
        log.info("开始查询会员积分流水: memberId={}, recordType={}, startTime={}, endTime={}, page={}/{}", 
                memberId, recordType, startTime, endTime, page.getCurrent(), page.getSize());
        
        LambdaQueryWrapper<MemberPointsRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MemberPointsRecord::getMemberId, memberId);
        
        if (recordType != null) {
            queryWrapper.eq(MemberPointsRecord::getRecordType, recordType);
        }
        
        if (startTime != null) {
            queryWrapper.ge(MemberPointsRecord::getGmtCreate, startTime);
        }
        
        if (endTime != null) {
            queryWrapper.le(MemberPointsRecord::getGmtCreate, endTime);
        }
        
        queryWrapper.orderByDesc(MemberPointsRecord::getGmtCreate);
        
        IPage<MemberPointsRecord> result = page(page, queryWrapper);
        log.info("会员积分流水查询完成: memberId={}, totalRecords={}, currentPage={}, totalPages={}", 
                memberId, result.getTotal(), result.getCurrent(), result.getPages());
        
        return result;
    }
    
    /**
     * 获取会员积分流水详情
     */
    public MemberPointsRecordDetail getRecordDetail(Long recordId) {
        log.info("开始获取会员积分流水详情: recordId={}", recordId);
        
        MemberPointsRecordDetail detail = detailService.getDetailByRecordId(recordId);
        
        if (detail != null) {
            log.info("获取到会员积分流水详情: recordId={}, detailId={}, bizId={}, bizType={}", 
                    recordId, detail.getId(), detail.getBizId(), detail.getBizType());
        } else {
            log.info("未找到会员积分流水详情: recordId={}", recordId);
        }
        
        return detail;
    }
    
    /**
     * 批量查询流水明细
     */
    public List<MemberPointsRecordDetail> listRecordDetails(List<Long> recordIds) {
        log.info("开始批量查询积分流水明细: recordIds={}", recordIds);
        
        List<MemberPointsRecordDetail> details = detailService.listDetailsByRecordIds(recordIds);
        
        log.info("批量查询积分流水明细完成: 请求记录数={}, 查询到记录数={}", 
                recordIds.size(), details.size());
        return details;
    }
} 