package com.ark.center.commodity.domain.brand.gateway;

import com.ark.center.commodity.client.brand.dto.BrandDTO;
import com.ark.center.commodity.client.brand.query.BrandPageQry;
import com.ark.center.commodity.domain.brand.Brand;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

public interface BrandGateway {

    IPage<BrandDTO> selectPages(BrandPageQry queryDTO);

    List<Brand> selectByIds(List<Long> ids);

    Brand selectById(Long id);

    List<Brand> selectList(BrandPageQry queryDTO);

    Long insert(Brand brand);

    boolean update(Brand brand);
}
