package com.ark.center.member.infra.point.service;

import com.ark.center.member.infra.point.MemberPointsRecordDetail;
import com.ark.center.member.infra.point.repository.db.mapper.MemberPointsRecordDetailMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberPointsRecordDetailService extends ServiceImpl<MemberPointsRecordDetailMapper, MemberPointsRecordDetail> {
    
    /**
     * 根据流水ID查询明细
     */
    public MemberPointsRecordDetail getDetailByRecordId(Long recordId) {
        log.info("开始根据流水ID查询明细: recordId=" + recordId);
        
        MemberPointsRecordDetail detail = lambdaQuery()
                .eq(MemberPointsRecordDetail::getRecordId, recordId)
                .one();
                
        if (detail != null) {
            log.info("查询到明细记录: detailId=" + detail.getId() + ", recordId=" + recordId);
        } else {
            log.info("未查询到明细记录: recordId=" + recordId);
        }
        
        return detail;
    }
    
    /**
     * 批量查询明细
     */
    public List<MemberPointsRecordDetail> listDetailsByRecordIds(List<Long> recordIds) {
        log.info("开始批量查询明细: recordIds数量=" + recordIds.size());
        
        List<MemberPointsRecordDetail> details = lambdaQuery()
                .in(MemberPointsRecordDetail::getRecordId, recordIds)
                .list();
                
        log.info("批量查询明细完成: 查询到记录数=" + details.size());
        return details;
    }
} 