package com.ark.center.commodity.domain.attr.repository;

import com.ark.center.commodity.client.attr.query.AttrPageQry;
import com.ark.center.commodity.domain.attr.aggregate.Attr;
import com.ark.ddd.base.Repository;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

public interface AttrRepository extends Repository<Attr, Long> {

    IPage<Attr> pageList(AttrPageQry queryDTO);

    List<Attr> findByGroupIds(List<Long> groupIds);
}
