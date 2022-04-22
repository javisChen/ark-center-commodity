package com.kt.cloud.commodity.module.attachment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kt.cloud.commodity.dao.entity.AttachmentDO;
import com.kt.cloud.commodity.dao.mapper.AttachmentMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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


    public void saveAttachments(String bizType, Long bizId, List<String> picList) {
        List<AttachmentDO> doList = picList.stream().map(item -> {
            AttachmentDO e = new AttachmentDO();
            e.setBizType(bizType);
            e.setBizId(bizId);
            e.setUrl(item);
            return e;
        }).collect(Collectors.toList());
        saveBatch(doList);
    }
}
