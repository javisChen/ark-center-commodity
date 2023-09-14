package com.ark.center.commodity.infra.attr.gateway.impl;


import com.ark.center.commodity.client.attr.query.AttrTemplatePageQry;
import com.ark.center.commodity.domain.attr.repository.AttrTemplateGateway;
import com.ark.center.commodity.infra.attr.convertor.AttrTemplateConvertor;
import com.ark.center.commodity.domain.attr.AttrTemplate;
import com.ark.center.commodity.infra.attr.gateway.db.AttrTemplateMapper;
import com.ark.component.orm.mybatis.base.BaseEntity;
import com.ark.component.web.util.bean.BeanConvertor;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 品牌表 服务实现类
 * </p>
 *
 * @author EOP
 * @since 2022-02-25
 */
@Service
@RequiredArgsConstructor
public class AttrTemplateGatewayImpl extends ServiceImpl<AttrTemplateMapper, AttrTemplate> implements IService<AttrTemplate>, AttrTemplateGateway {

    private final AttrTemplateConvertor convertor;

    @Override
    public IPage<com.ark.center.commodity.domain.attr.aggregate.AttrTemplate> selectPages(AttrTemplatePageQry queryDTO) {
        return lambdaQuery()
                .like(StringUtils.isNotEmpty(queryDTO.getName()), AttrTemplate::getName, queryDTO.getName())
                .page(new PageDTO<>(queryDTO.getCurrent(), queryDTO.getSize()))
                .convert(item -> BeanConvertor.copy(item, com.ark.center.commodity.domain.attr.aggregate.AttrTemplate.class));
    }

    @Override
    public Long countById(Long attrTemplateId) {
        return lambdaQuery().eq(BaseEntity::getId, attrTemplateId).count();
    }

    @Override
    public Long store(com.ark.center.commodity.domain.attr.aggregate.AttrTemplate aggregate) {
        AttrTemplate entity = convertor.fromDomain(aggregate);
        save(entity);
        return entity.getId();
    }

    @Override
    public com.ark.center.commodity.domain.attr.aggregate.AttrTemplate selectById(Long id) {
        AttrTemplate brand = getById(id);
        return convertor.toAggregate(brand);
    }

    @Override
    public boolean update(com.ark.center.commodity.domain.attr.aggregate.AttrTemplate aggregate) {
        AttrTemplate entity = convertor.fromDomain(aggregate);
        return updateById(entity);
    }

    @Override
    public boolean remove(Long id) {
        return removeById(id);
    }


}
