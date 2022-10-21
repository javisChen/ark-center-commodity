package com.ark.center.commodity.infrastructure.attr.convertor;

import com.ark.center.commodity.domain.attr.aggregate.AttrTemplate;
import com.ark.center.commodity.infrastructure.attr.repository.db.AttrTemplateDO;
import com.ark.ddd.base.RepositoryConvertor;
import org.springframework.stereotype.Component;

@Component
public class AttrTemplateConvertor extends RepositoryConvertor<AttrTemplate, AttrTemplateDO> {

}
