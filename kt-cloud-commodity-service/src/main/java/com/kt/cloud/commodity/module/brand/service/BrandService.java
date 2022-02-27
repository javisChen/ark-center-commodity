package com.kt.cloud.commodity.module.brand.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kt.cloud.commodity.dao.entity.Brand;
import com.kt.cloud.commodity.dao.mapper.BrandMapper;
import com.kt.cloud.commodity.module.brand.dto.request.BrandCreateReqDTO;
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

    public Long create(BrandCreateReqDTO reqDTO) {
        return null;
    }
}
