package com.ark.center.commodity.domain.attr.repository;

import com.ark.center.commodity.client.attr.query.AttrTemplatePageQry;
import com.ark.center.commodity.domain.attr.aggregate.AttrTemplate;
import com.ark.ddd.base.Repository;
import com.baomidou.mybatisplus.core.metadata.IPage;

public interface AttrTemplateGateway extends Repository<AttrTemplate, Long> {

    IPage<AttrTemplate> selectPages(AttrTemplatePageQry queryDTO);

    Long countById(Long attrTemplateId);
}
