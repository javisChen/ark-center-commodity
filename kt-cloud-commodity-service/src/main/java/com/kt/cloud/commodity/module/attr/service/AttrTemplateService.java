package com.kt.cloud.commodity.module.attr.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kt.cloud.commodity.dao.entity.AttrTemplateDO;
import com.kt.cloud.commodity.dao.mapper.AttrTemplateMapper;
import com.kt.cloud.commodity.module.attr.dto.request.AttrTemplateCreateReqDTO;
import com.kt.cloud.commodity.module.attr.dto.request.AttrTemplatePageQueryReqDTO;
import com.kt.cloud.commodity.module.attr.dto.request.AttrTemplateUpdateReqDTO;
import com.kt.cloud.commodity.module.attr.dto.response.AttrTemplateRespDTO;
import com.kt.component.common.ParamsChecker;
import com.kt.component.dto.PageResponse;
import com.kt.component.exception.ExceptionFactory;
import com.kt.component.web.util.bean.BeanConvertor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品属性模板 服务实现类
 * </p>
 *
 * @author EOP
 * @since 2022-03-05
 */
@Service
public class AttrTemplateService extends ServiceImpl<AttrTemplateMapper, AttrTemplateDO> implements IService<AttrTemplateDO> {

    public Long createAttrTemplate(AttrTemplateCreateReqDTO reqDTO) {
        AttrTemplateDO entity = BeanConvertor.copy(reqDTO, AttrTemplateDO.class);
        save(entity);
        return entity.getId();
    }

    public PageResponse<AttrTemplateRespDTO> getPageList(AttrTemplatePageQueryReqDTO queryDTO) {
        IPage<AttrTemplateRespDTO> page = lambdaQuery()
                .page(new Page<>(queryDTO.getCurrent(), queryDTO.getSize()))
                .convert(item -> BeanConvertor.copy(item, AttrTemplateRespDTO.class));
        return BeanConvertor.copyPage(page, AttrTemplateRespDTO.class);
    }

    public Long updateAttrTemplate(AttrTemplateUpdateReqDTO reqDTO) {
        AttrTemplateDO entity = BeanConvertor.copy(reqDTO, AttrTemplateDO.class);
        updateById(entity);
        return entity.getId();
    }

    public AttrTemplateRespDTO getAttrTemplateInfo(Long AttrTemplateId) {
        AttrTemplateDO entity = getById(AttrTemplateId);
        return BeanConvertor.copy(entity, AttrTemplateRespDTO.class);
    }

    public void checkTemplateExists(Long attrTemplateId) {
        AttrTemplateDO attrTemplateDO = getById(attrTemplateId);
        ParamsChecker.throwIfIsNull(attrTemplateDO, ExceptionFactory.userException("属性模板不存在"));
    }
}
