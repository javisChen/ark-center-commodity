package com.ark.center.commodity.domain.attr.repository;

import com.ark.center.commodity.client.attr.dto.AttrGroupDTO;
import com.ark.center.commodity.client.attr.query.AttrGroupPageQry;
import com.ark.center.commodity.domain.attr.AttrGroup;
import com.baomidou.mybatisplus.core.metadata.IPage;

public interface AttrGroupGateway {
    Long insert(AttrGroup attrGroup);

    AttrGroup selectById(Long id);

    boolean update(AttrGroup aggregate);

    boolean remove(Long id);

    IPage<AttrGroupDTO> selectPages(AttrGroupPageQry queryDTO);
    Long countById(Long attrGroupId);

}
