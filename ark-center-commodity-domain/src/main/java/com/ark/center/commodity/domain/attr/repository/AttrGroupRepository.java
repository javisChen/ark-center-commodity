package com.ark.center.commodity.domain.attr.repository;

import com.ark.center.commodity.client.attr.query.AttrGroupPageQry;
import com.ark.center.commodity.domain.attr.aggregate.AttrGroup;
import com.ark.ddd.base.Repository;
import com.baomidou.mybatisplus.core.metadata.IPage;

public interface AttrGroupRepository extends Repository<AttrGroup, Long> {

    IPage<AttrGroup> pageList(AttrGroupPageQry queryDTO);

}
