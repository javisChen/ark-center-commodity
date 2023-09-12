package com.ark.center.commodity.infra.attr.repository.impl;


import com.ark.center.commodity.client.attr.query.AttrTemplatePageQry;
import com.ark.center.commodity.domain.attr.aggregate.AttrTemplate;
import com.ark.center.commodity.domain.attr.repository.AttrTemplateRepository;
import com.ark.center.commodity.infra.attr.convertor.AttrTemplateConvertor;
import com.ark.center.commodity.domain.attr.AttrTemplateDO;
import com.ark.center.commodity.infra.attr.repository.db.AttrTemplateMapper;
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
public class AttrTemplateRepositoryImpl extends ServiceImpl<AttrTemplateMapper, AttrTemplateDO> implements IService<AttrTemplateDO>, AttrTemplateRepository {

    private final AttrTemplateConvertor convertor;

    @Override
    public IPage<AttrTemplate> pageList(AttrTemplatePageQry queryDTO) {
        return lambdaQuery()
                .like(StringUtils.isNotEmpty(queryDTO.getName()), AttrTemplateDO::getName, queryDTO.getName())
                .page(new PageDTO<>(queryDTO.getCurrent(), queryDTO.getSize()))
                .convert(item -> BeanConvertor.copy(item, AttrTemplate.class));
    }

    @Override
    public Long countById(Long attrTemplateId) {
        return lambdaQuery().eq(BaseEntity::getId, attrTemplateId).count();
    }

    @Override
    public Long store(AttrTemplate aggregate) {
        AttrTemplateDO entity = convertor.fromDomain(aggregate);
        save(entity);
        return entity.getId();
    }

    @Override
    public AttrTemplate selectById(Long id) {
        AttrTemplateDO brand = getById(id);
        return convertor.toAggregate(brand);
    }

    @Override
    public boolean update(AttrTemplate aggregate) {
        AttrTemplateDO entity = convertor.fromDomain(aggregate);
        return updateById(entity);
    }

    @Override
    public boolean remove(Long id) {
        return removeById(id);
    }


}
