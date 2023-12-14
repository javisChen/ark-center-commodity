package com.ark.center.product.domain.attr.repository;

import com.ark.center.product.client.attr.dto.AttrTemplateDTO;
import com.ark.center.product.client.attr.query.AttrTemplatePageQry;
import com.ark.center.product.domain.attr.AttrTemplate;
import com.baomidou.mybatisplus.core.metadata.IPage;

public interface AttrTemplateGateway {

    IPage<AttrTemplateDTO> selectPages(AttrTemplatePageQry queryDTO);

    Long countById(Long attrTemplateId);

    Long insert(AttrTemplate attrTemplate);

    AttrTemplate selectById(Long id);

    boolean remove(Long id);

    void update(AttrTemplate attrTemplate);
}
