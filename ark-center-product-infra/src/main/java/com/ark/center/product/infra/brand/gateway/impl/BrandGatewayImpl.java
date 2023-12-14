package com.ark.center.product.infra.brand.gateway.impl;

import com.ark.center.product.client.brand.dto.BrandDTO;
import com.ark.center.product.client.brand.query.BrandPageQry;
import com.ark.center.product.domain.brand.Brand;
import com.ark.center.product.domain.brand.gateway.BrandGateway;
import com.ark.center.product.infra.brand.convertor.BrandConvertor;
import com.ark.center.product.infra.brand.gateway.db.BrandMapper;
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

    private final BrandConvertor brandConvertor;

    @Override
    public IPage<BrandDTO> selectPages(BrandPageQry queryDTO) {
        return lambdaQuery()
                .like(StringUtils.isNotEmpty(queryDTO.getName()), Brand::getName, queryDTO.getName())
                .page(new PageDTO<>(queryDTO.getCurrent(), queryDTO.getSize()))
                .convert(brandConvertor::toDTO)
                ;
    }

    public Long insert(Brand brand) {
        save(brand);
        return brand.getId();
    }

    public Brand queryById(Long id) {
        return getById(id);
    }

    public boolean update(Brand brand) {
        return updateById(brand);
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
    public Brand selectById(Long id) {
        return lambdaQuery().eq(BaseEntity::getId, id).one();
    }


    @Override
    public List<Brand> selectList(BrandPageQry queryDTO) {
        return null;
    }
}
