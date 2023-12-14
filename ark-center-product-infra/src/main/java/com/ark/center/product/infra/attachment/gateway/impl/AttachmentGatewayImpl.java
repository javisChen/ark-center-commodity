package com.ark.center.product.infra.attachment.gateway.impl;

import com.ark.center.product.domain.attachment.Attachment;
import com.ark.center.product.domain.attachment.gateway.AttachmentGateway;
import com.ark.center.product.infra.attachment.gateway.db.AttachmentMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AttachmentGatewayImpl extends ServiceImpl<AttachmentMapper, Attachment> implements AttachmentGateway {

    @Override
    public List<Attachment> selectByBizTypeAndBizId(String bizType, Long bizId) {
        return lambdaQuery()
                .select(Attachment::getId,
                        Attachment::getBizId,
                        Attachment::getBizType,
                        Attachment::getUrl)
                .eq(Attachment::getBizType, bizType)
                .eq(Attachment::getBizId, bizId)
                .list();
    }

    @Override
    public List<Attachment> selectByBizTypeAndBizIds(String bizType, List<Long> bizIds) {
        return lambdaQuery()
                .select(Attachment::getId,
                        Attachment::getBizId,
                        Attachment::getBizType,
                        Attachment::getUrl)
                .eq(Attachment::getBizType, bizType)
                .in(Attachment::getBizId, bizIds)
                .list();
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        removeByIds(ids);
    }

    @Override
    public void insert(Attachment attachment) {
        save(attachment);
    }

}
