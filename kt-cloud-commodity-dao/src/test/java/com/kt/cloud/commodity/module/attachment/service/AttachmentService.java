package com.kt.cloud.commodity.module.attachment.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kt.cloud.commodity.dao.entity.AttachmentDO;
import com.kt.cloud.commodity.dao.mapper.AttachmentMapper;
import com.kt.cloud.commodity.module.attachment.dto.request.AttachmentCreateReqDTO;
import com.kt.cloud.commodity.module.attachment.dto.request.AttachmentUpdateReqDTO;
import com.kt.cloud.commodity.module.attachment.dto.request.AttachmentPageQueryReqDTO;
import com.kt.cloud.commodity.module.attachment.dto.response.AttachmentRespDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kt.component.dto.PageResponse;
import org.springframework.stereotype.Service;
import com.kt.component.web.util.bean.BeanConvertor;

/**
 * <p>
 * 附件表 服务实现类
 * </p>
 *
 * @author EOP
 * @since 2022-04-22
 */
@Service
public class AttachmentService extends ServiceImpl<AttachmentMapper, AttachmentDO> implements IService<AttachmentDO> {

    public Long createAttachment(AttachmentUpdateReqDTO reqDTO) {
        AttachmentDO entity = BeanConvertor.copy(reqDTO, AttachmentDO.class);
        save(entity);
        return entity.getId();
    }

    public PageResponse<AttachmentRespDTO> getPageList(AttachmentPageQueryReqDTO queryDTO) {
        IPage<AttachmentRespDTO> page = lambdaQuery()
                .orderByDesc(BaseEntity::getGmtCreate)
                .page(new Page<>(queryDTO.getCurrent(), queryDTO.getSize()))
                .convert(item -> BeanConvertor.copy(item, AttachmentRespDTO.class));
        return BeanConvertor.copyPage(page, AttachmentRespDTO.class);
    }

    public Long updateAttachment(AttachmentUpdateReqDTO reqDTO) {
        AttachmentDO entity = BeanConvertor.copy(reqDTO, AttachmentDO.class);
        updateById(entity);
        return entity.getId();
    }

    public AttachmentRespDTO getAttachmentInfo(Long AttachmentId) {
        AttachmentDO entity = getById(AttachmentId);
        return BeanConvertor.copy(entity, AttachmentRespDTO.class);
    }

}
