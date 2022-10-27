package com.ark.center.commodity.domain.commodity.repository;

import com.ark.center.commodity.client.commodity.query.CommodityPageQry;
import com.ark.center.commodity.domain.commodity.aggregate.Commodity;
import com.ark.ddd.base.Repository;
import com.baomidou.mybatisplus.core.metadata.IPage;

public interface CommodityRepository extends Repository<Commodity, Long> {

    IPage<Commodity> getPageList(CommodityPageQry queryDTO);
}
