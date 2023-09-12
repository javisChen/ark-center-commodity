package com.ark.center.commodity.infra.brand.repository.impl;

import com.ark.center.commodity.client.brand.query.BrandPageQry;
import com.ark.center.commodity.domain.brand.Brand;
import com.ark.center.commodity.domain.brand.repository.BrandGateway;
import com.ark.center.commodity.infra.brand.repository.db.BrandMapper;
import com.ark.component.orm.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandGatewayImpl extends ServiceImpl<BrandMapper, Brand> implements IService<Brand>, BrandGateway {

    @Override
    public IPage<Brand> selectPages(BrandPageQry queryDTO) {
        return lambdaQuery()
                .like(StringUtils.isNotEmpty(queryDTO.getName()), Brand::getName, queryDTO.getName())
                .page(new PageDTO<>(queryDTO.getCurrent(), queryDTO.getSize()));
    }

    public Long insert(Brand entity) {
        save(entity);
        return entity.getId();
    }

    public Brand queryById(Long id) {
        return getById(id);
    }

    public boolean update(Brand entity) {
        return updateById(entity);
    }

    public boolean remove(Long id) {
        return removeById(id);
    }

    @Override
    public List<Brand> selectByIds(List<Long> ids) {
        return lambdaQuery()
                .in(BaseEntity::getId, ids)
                .list();
    }


    @Override
    public List<Brand> selectList(BrandPageQry queryDTO) {
        return null;
    }
}
