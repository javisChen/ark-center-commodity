package com.kt.cloud.commodity.module.brand.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kt.cloud.commodity.dao.entity.Brand;
import com.kt.cloud.commodity.dao.mapper.BrandMapper;
import com.kt.cloud.commodity.module.brand.dto.request.cmd.BrandCreateReqDTO;
import com.kt.cloud.commodity.module.brand.dto.request.cmd.BrandUpdateReqDTO;
import com.kt.cloud.commodity.module.brand.dto.request.query.BrandPageQueryDTO;
import com.kt.cloud.commodity.module.brand.dto.response.BrandRespDTO;
import com.kt.component.web.util.bean.BeanConvertor;
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
public class BrandService extends ServiceImpl<BrandMapper, Brand> implements IService<Brand> {

    public Long createBrand(BrandCreateReqDTO reqDTO) {
        Brand entity = BeanConvertor.copy(reqDTO, Brand.class);
        save(entity);
        return entity.getId();
    }

    public IPage<BrandRespDTO> getPageList(BrandPageQueryDTO queryDTO) {
        return lambdaQuery()
                .like(StringUtils.isNotEmpty(queryDTO.getName()), Brand::getName, queryDTO.getName())
                .page(new PageDTO<>(queryDTO.getCurrent(), queryDTO.getSize()))
                .convert(item -> BeanConvertor.copy(item, BrandRespDTO.class));
    }

    public Long updateBrand(BrandUpdateReqDTO reqDTO) {
        Brand entity = BeanConvertor.copy(reqDTO, Brand.class);
        updateById(entity);
        return entity.getId();
    }

    public BrandRespDTO getBrandInfo(Long brandId) {
        Brand brand = getById(brandId);
        return BeanConvertor.copy(brand, BrandRespDTO.class);
    }
}
