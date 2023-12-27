package com.ark.center.product.infra.attr.gateway.impl;


import com.ark.center.product.client.attr.dto.AttrTemplateDTO;
import com.ark.center.product.client.attr.query.AttrTemplatePageQry;
import com.ark.center.product.domain.attr.gateway.AttrTemplateGateway;
import com.ark.center.product.infra.attr.convertor.AttrTemplateConverter;
import com.ark.center.product.domain.attr.AttrTemplate;
import com.ark.center.product.infra.attr.gateway.db.AttrTemplateMapper;
import com.ark.component.orm.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AttrTemplateGatewayImpl extends ServiceImpl<AttrTemplateMapper, AttrTemplate> implements IService<AttrTemplate>, AttrTemplateGateway {

    private final AttrTemplateConverter convertor;

    @Override
    public IPage<AttrTemplateDTO> selectPages(AttrTemplatePageQry queryDTO) {
        return lambdaQuery()
                .like(StringUtils.isNotEmpty(queryDTO.getName()), AttrTemplate::getName, queryDTO.getName())
                .page(new PageDTO<>(queryDTO.getCurrent(), queryDTO.getSize()))
                .convert(convertor::toDTO);
    }

    @Override
    public Long countById(Long attrTemplateId) {
        return lambdaQuery().eq(BaseEntity::getId, attrTemplateId).count();
    }

    @Override
    public Long insert(AttrTemplate attrTemplate) {
        save(attrTemplate);
        return attrTemplate.getId();
    }

    @Override
    public void update(AttrTemplate attrTemplate) {
        updateById(attrTemplate);
    }

    @Override
    public AttrTemplate selectById(Long id) {
        return getById(id);
    }

    @Override
    public boolean remove(Long id) {
        return removeById(id);
    }


}
