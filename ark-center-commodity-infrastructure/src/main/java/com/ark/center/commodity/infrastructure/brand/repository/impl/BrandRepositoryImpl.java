package com.ark.center.commodity.infrastructure.brand.repository.impl;

import com.ark.center.commodity.client.brand.query.BrandPageQry;
import com.ark.center.commodity.domain.brand.aggregate.Brand;
import com.ark.center.commodity.domain.brand.repository.BrandRepository;
import com.ark.center.commodity.infrastructure.brand.convertor.BrandConvertor;
import com.ark.center.commodity.infrastructure.brand.repository.db.BrandDO;
import com.ark.center.commodity.infrastructure.brand.repository.db.BrandMapper;
import com.ark.component.web.util.bean.BeanConvertor;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

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
public class BrandRepositoryImpl extends ServiceImpl<BrandMapper, BrandDO> implements IService<BrandDO>, BrandRepository {

    private final BrandConvertor convertor;

    @Override
    public IPage<Brand> pageList(BrandPageQry queryDTO) {
        return lambdaQuery()
                .like(StringUtils.isNotEmpty(queryDTO.getName()), BrandDO::getName, queryDTO.getName())
                .page(new PageDTO<>(queryDTO.getCurrent(), queryDTO.getSize()))
                .convert(item -> BeanConvertor.copy(item, Brand.class));
    }

    @Override
    public Long store(Brand aggregate) {
        BrandDO entity = convertor.fromAggregate(aggregate);
        save(entity);
        return entity.getId();
    }

    @Override
    public Brand findById(Long id) {
        BrandDO brand = getById(id);
        return convertor.toAggregate(brand);
    }

    @Override
    public boolean update(Brand aggregate) {
        BrandDO entity = convertor.fromAggregate(aggregate);
        return updateById(entity);
    }

    @Override
    public boolean remove(Long id) {
        return removeById(id);
    }


    @Override
    public List<Brand> list(BrandPageQry queryDTO) {
        return null;
    }
}
