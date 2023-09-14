package com.ark.center.commodity.infra.attr.gateway.impl;

import com.ark.center.commodity.client.attr.query.AttrGroupPageQry;
import com.ark.center.commodity.domain.attr.repository.AttrGroupGateway;
import com.ark.center.commodity.infra.attr.convertor.AttrGroupConvertor;
import com.ark.center.commodity.domain.attr.AttrGroup;
import com.ark.center.commodity.infra.attr.gateway.db.AttrGroupMapper;
import com.ark.component.orm.mybatis.base.BaseEntity;
import com.ark.component.web.util.bean.BeanConvertor;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 品牌表 服务实现类
 * </p>
 *
 * @author EOP
 * @since 2022-02-25
 */
@Component
@RequiredArgsConstructor
public class AttrGroupGatewayImpl extends ServiceImpl<AttrGroupMapper, AttrGroup> implements IService<AttrGroup>, AttrGroupGateway {

    private final AttrGroupConvertor convertor;

    @Override
    public Long store(com.ark.center.commodity.domain.attr.aggregate.AttrGroup aggregate) {
        AttrGroup entity = convertor.fromDomain(aggregate);
        save(entity);
        return entity.getId();
    }

    @Override
    public com.ark.center.commodity.domain.attr.aggregate.AttrGroup selectById(Long id) {
        AttrGroup dataObject = getById(id);
        return convertor.toAggregate(dataObject);
    }

    @Override
    public boolean update(com.ark.center.commodity.domain.attr.aggregate.AttrGroup aggregate) {
        AttrGroup entity = convertor.fromDomain(aggregate);
        return updateById(entity);
    }

    @Override
    public boolean remove(Long id) {
        return removeById(id);
    }

    @Override
    public IPage<com.ark.center.commodity.domain.attr.aggregate.AttrGroup> selectPages(AttrGroupPageQry queryDTO) {
        if (queryDTO.getCategoryId() != null) {
//            CategoryDO categoryDO = Optional.ofNullable(categoryAdminService.getById(queryDTO.getCategoryId()))
//                    .orElseThrow(() -> ExceptionFactory.userException("商品类目不存在"));
            queryDTO.setAttrTemplateId(queryDTO.getAttrTemplateId());
        }
        return lambdaQuery()
                .like(StringUtils.isNotEmpty(queryDTO.getName()), AttrGroup::getName, queryDTO.getName())
                .eq(AttrGroup::getAttrTemplateId, queryDTO.getAttrTemplateId())
                .page(new Page<>(queryDTO.getCurrent(), queryDTO.getSize()))
                .convert(item -> BeanConvertor.copy(item, com.ark.center.commodity.domain.attr.aggregate.AttrGroup.class));
    }

    @Override
    public Long countById(Long attrGroupId) {
        if (attrGroupId == null) {
            return 0L;
        }
        return lambdaQuery().eq(BaseEntity::getId, attrGroupId).count();
    }
}
