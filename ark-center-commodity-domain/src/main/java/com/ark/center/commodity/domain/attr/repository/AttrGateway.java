package com.ark.center.commodity.domain.attr.repository;

import com.ark.center.commodity.client.attr.dto.AttrDTO;
import com.ark.center.commodity.client.attr.query.AttrPageQry;
import com.ark.center.commodity.domain.attr.Attr;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

public interface AttrGateway {

    IPage<Attr> selectPages(AttrPageQry queryDTO);

    List<Attr> selectByGroupIds(List<Long> groupIds);

    AttrDTO selectById(Long attrId);
}
