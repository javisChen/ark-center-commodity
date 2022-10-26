package com.ark.center.commodity.infrastructure.commodity.convertor;

import com.ark.center.commodity.domain.commodity.aggregate.Commodity;
import com.ark.center.commodity.infrastructure.category.repository.db.CategoryDO;
import com.ark.ddd.base.RepositoryConvertor;
import org.springframework.stereotype.Component;

@Component
public class CommodityConvertor extends RepositoryConvertor<Commodity, CategoryDO> {


}
