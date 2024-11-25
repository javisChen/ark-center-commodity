package com.ark.center.product.infra.attr.gateway;

import com.ark.center.product.client.attr.dto.AttrGroupDTO;
import com.ark.center.product.client.attr.query.AttrGroupPageQry;
import com.ark.center.product.infra.attr.AttrGroup;
import com.baomidou.mybatisplus.core.metadata.IPage;

public interface AttrGroupGateway {
    Long insert(AttrGroup attrGroup);

    AttrGroup selectById(Long id);

    boolean update(AttrGroup aggregate);

    boolean remove(Long id);

    IPage<AttrGroupDTO> selectPages(AttrGroupPageQry queryDTO);
    Long countById(Long attrGroupId);

}
