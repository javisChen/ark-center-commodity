package com.ark.center.commodity.infrastructure.attr.convertor;

import com.ark.center.commodity.domain.attr.aggregate.Attr;
import com.ark.center.commodity.infrastructure.attr.repository.db.AttrDO;
import com.ark.ddd.base.RepositoryConvertor;
import org.springframework.stereotype.Component;

@Component
public class AttrConvertor extends RepositoryConvertor<Attr, AttrDO> {

}
