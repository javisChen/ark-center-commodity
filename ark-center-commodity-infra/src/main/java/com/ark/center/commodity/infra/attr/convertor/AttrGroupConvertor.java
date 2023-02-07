package com.ark.center.commodity.infra.attr.convertor;

import com.ark.center.commodity.domain.attr.aggregate.AttrGroup;
import com.ark.center.commodity.infra.attr.repository.db.AttrGroupDO;
import com.ark.ddd.base.RepositoryConvertor;
import org.springframework.stereotype.Component;

@Component
public class AttrGroupConvertor extends RepositoryConvertor<AttrGroup, AttrGroupDO> {

}
