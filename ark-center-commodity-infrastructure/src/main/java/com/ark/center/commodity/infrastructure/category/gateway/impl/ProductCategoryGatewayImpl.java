package com.ark.center.commodity.infrastructure.category.gateway.impl;

import com.ark.center.commodity.client.category.query.CategoryPageQuery;
import com.ark.center.commodity.domain.category.aggregate.ProductCategory;
import com.ark.center.commodity.domain.category.gateway.ProductCategoryGateway;
import com.ark.center.commodity.infrastructure.db.dataobject.CategoryDO;
import com.ark.center.commodity.infrastructure.db.mapper.CategoryMapper;
import com.ark.component.dto.PageResponse;
import com.ark.component.orm.mybatis.base.BaseEntity;
import com.ark.component.web.util.bean.BeanConvertor;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ProductCategoryGatewayImpl extends ServiceImpl<CategoryMapper, CategoryDO> implements IService<CategoryDO>, ProductCategoryGateway {

    @Override
    public PageResponse<ProductCategory> pageList(CategoryPageQuery queryDTO) {
        IPage<CategoryDO> page = lambdaQuery()
                .eq(Objects.nonNull(queryDTO.getLevel()), CategoryDO::getLevel, queryDTO.getLevel())
                .eq(Objects.nonNull(queryDTO.getPid()), CategoryDO::getPid, queryDTO.getPid())
                .orderByDesc(BaseEntity::getGmtCreate)
                .page(new Page<>(queryDTO.getCurrent(), queryDTO.getSize()))
                .convert(item -> BeanConvertor.copy(item, CategoryDO.class))
        return page;
    }
}
