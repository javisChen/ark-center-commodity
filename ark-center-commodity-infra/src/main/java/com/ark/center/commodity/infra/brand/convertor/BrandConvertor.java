package com.ark.center.commodity.infra.brand.convertor;

import com.ark.center.commodity.domain.brand.aggregate.Brand;
import com.ark.center.commodity.infra.brand.repository.db.BrandDO;
import com.ark.ddd.base.RepositoryConvertor;
import org.springframework.stereotype.Component;

@Component
public class BrandConvertor extends RepositoryConvertor<Brand, BrandDO> {

}