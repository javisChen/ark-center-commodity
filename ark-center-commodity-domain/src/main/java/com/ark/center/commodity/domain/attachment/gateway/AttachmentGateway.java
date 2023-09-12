package com.ark.center.commodity.domain.attachment.gateway;

import com.ark.center.commodity.domain.attachment.Attachment;

import java.util.List;

public interface AttachmentGateway {

    List<Attachment> selectByBizTypeAndBizId(String bizType, Long bizId);

    void deleteByIds(List<Long> ids);

    void insert(Attachment attachment);
}
