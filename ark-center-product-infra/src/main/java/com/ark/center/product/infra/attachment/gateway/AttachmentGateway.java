package com.ark.center.product.infra.attachment.gateway;

import com.ark.center.product.infra.attachment.Attachment;

import java.util.List;

public interface AttachmentGateway {

    List<Attachment> selectByBizTypeAndBizId(String bizType, Long bizId);

    List<Attachment> selectByBizTypeAndBizIds(String bizType, List<Long> bizIds);

    void deleteByIds(List<Long> ids);

    void insert(Attachment attachment);

    void batchInsert(List<Attachment> attachments);
}
