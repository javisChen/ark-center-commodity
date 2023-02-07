package com.ark.center.commodity.module.attr.service;

import com.ark.center.commodity.infra.attr.repository.db.AttrTemplateDO;
import com.ark.center.commodity.infra.attr.repository.db.AttrTemplateMapper;
import com.ark.center.commodity.module.attr.dto.request.AttrTemplateCreateReqDTO;
import com.ark.center.commodity.module.attr.dto.request.AttrTemplatePageQueryReqDTO;
import com.ark.center.commodity.module.attr.dto.request.AttrTemplateUpdateReqDTO;
import com.ark.center.commodity.module.attr.dto.response.AttrTemplateRespDTO;
import com.ark.component.common.ParamsChecker;
import com.ark.component.dto.PageResponse;
import com.ark.component.exception.ExceptionFactory;
import com.ark.component.orm.mybatis.base.BaseEntity;
import com.ark.component.web.util.bean.BeanConvertor;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

    public Long countById(Long attrTemplateId) {
        return lambdaQuery().eq(BaseEntity::getId, attrTemplateId).count();
    }

}
