package com.ark.center.commodity.infra.attr.gateway.impl;

import com.ark.center.commodity.client.attr.dto.AttrGroupDTO;
import com.ark.center.commodity.client.attr.query.AttrGroupPageQry;
import com.ark.center.commodity.domain.attr.repository.AttrGroupGateway;
import com.ark.center.commodity.infra.attr.convertor.AttrGroupConverter;
import com.ark.center.commodity.domain.attr.AttrGroup;
import com.ark.center.commodity.infra.attr.gateway.db.AttrGroupMapper;
import com.ark.component.orm.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AttrGroupGatewayImpl extends ServiceImpl<AttrGroupMapper, AttrGroup> implements IService<AttrGroup>, AttrGroupGateway {

    private final AttrGroupConverter converter;

    @Override
    public Long insert(com.ark.center.commodity.domain.attr.AttrGroup attrGroup) {
        save(attrGroup);
        return attrGroup.getId();
    }

    @Override
    public com.ark.center.commodity.domain.attr.AttrGroup selectById(Long id) {
        return getById(id);
    }

    @Override
    public boolean update(com.ark.center.commodity.domain.attr.AttrGroup aggregate) {
        return updateById(aggregate);
    }

    @Override
    public boolean remove(Long id) {
        return removeById(id);
    }

    @Override
    public IPage<AttrGroupDTO> selectPages(AttrGroupPageQry qry) {
        if (qry.getCategoryId() != null) {
            qry.setAttrTemplateId(qry.getAttrTemplateId());
        }
        return lambdaQuery()
                .like(StringUtils.isNotEmpty(qry.getName()), AttrGroup::getName, qry.getName())
                .eq(AttrGroup::getAttrTemplateId, qry.getAttrTemplateId())
                .page(new Page<>(qry.getCurrent(), qry.getSize()))
                .convert(converter::toDTO);
    }

    @Override
    public Long countById(Long attrGroupId) {
        if (attrGroupId == null) {
            return 0L;
        }
        return lambdaQuery().eq(BaseEntity::getId, attrGroupId).count();
    }
}
