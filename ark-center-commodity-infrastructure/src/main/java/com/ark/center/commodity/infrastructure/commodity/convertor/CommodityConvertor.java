package com.ark.center.commodity.infrastructure.commodity.convertor;

import com.ark.center.commodity.domain.commodity.aggregate.Commodity;
import com.ark.center.commodity.infrastructure.category.repository.db.CategoryDO;
import com.ark.center.commodity.infrastructure.commodity.repository.db.SpuDO;
import com.ark.component.web.util.bean.BeanConvertor;
import com.ark.ddd.base.RepositoryConvertor;
import org.springframework.stereotype.Component;

@Component
public class CommodityConvertor extends RepositoryConvertor<Commodity, CategoryDO> {

    public SpuDO convertToSpuDO(Commodity commodity) {
        SpuDO spuDO = BeanConvertor.copy(commodity, SpuDO.class);
        spuDO.setShelfStatus(commodity.getShelfStatus().getValue());
        spuDO.setVerifyStatus(commodity.getVerifyStatus().getValue());
        return spuDO;
    }
}
