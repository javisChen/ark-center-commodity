package com.ark.center.commodity.domain.brand.repository;

import com.ark.center.commodity.client.brand.query.BrandPageQry;
import com.ark.center.commodity.domain.brand.aggregate.Brand;
import com.ark.ddd.base.Repository;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

public interface BrandRepository extends Repository<Brand, Long> {

    IPage<Brand> pageList(BrandPageQry queryDTO);


    List<Brand> list(BrandPageQry queryDTO);

}
