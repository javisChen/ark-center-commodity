package com.ark.center.commodity.domain.brand.repository;

import com.ark.center.commodity.client.brand.query.BrandPageQry;
import com.ark.center.commodity.domain.brand.Brand;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

public interface BrandGateway {

    IPage<Brand> selectPages(BrandPageQry queryDTO);

    List<Brand> selectByIds(List<Long> ids);

    List<Brand> selectList(BrandPageQry queryDTO);

}
