package com.kt.cloud.commodity.module.brand.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kt.cloud.commodity.dao.entity.BrandDO;
import com.kt.cloud.commodity.dao.mapper.BrandMapper;
import com.kt.cloud.commodity.module.brand.dto.request.BrandCreateReqDTO;
import com.kt.cloud.commodity.module.brand.dto.request.BrandUpdateReqDTO;
import com.kt.cloud.commodity.module.brand.dto.request.BrandPageQueryReqDTO;
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
public class BrandService extends ServiceImpl<BrandMapper, BrandDO> implements IService<BrandDO> {

    public Long createBrand(BrandCreateReqDTO reqDTO) {
        BrandDO entity = BeanConvertor.copy(reqDTO, BrandDO.class);
        save(entity);
        return entity.getId();
    }

    public IPage<BrandRespDTO> getPageList(BrandPageQueryReqDTO queryDTO) {
        return lambdaQuery()
                .like(StringUtils.isNotEmpty(queryDTO.getName()), BrandDO::getName, queryDTO.getName())
                .page(new PageDTO<>(queryDTO.getCurrent(), queryDTO.getSize()))
                .convert(item -> BeanConvertor.copy(item, BrandRespDTO.class));
    }

    public Long updateBrand(BrandUpdateReqDTO reqDTO) {
        BrandDO entity = BeanConvertor.copy(reqDTO, BrandDO.class);
        updateById(entity);
        return entity.getId();
    }

    public BrandRespDTO getBrandInfo(Long brandId) {
        BrandDO brand = getById(brandId);
        return BeanConvertor.copy(brand, BrandRespDTO.class);
    }
}
