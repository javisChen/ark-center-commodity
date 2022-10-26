package com.ark.center.commodity.infrastructure.attr.convertor;

import com.ark.center.commodity.domain.attr.aggregate.Attr;
import com.ark.center.commodity.infrastructure.attr.repository.db.AttrDO;
import com.ark.ddd.base.RepositoryConvertor;
import org.springframework.stereotype.Component;

@Component
public class AttrConvertor extends RepositoryConvertor<Attr, AttrDO> {

    @Override
    public Attr toAggregate(AttrDO dataObject) {
        Attr attr = super.toAggregate(dataObject);
        attr.setInputType(Attr.InputType.getByValue(dataObject.getInputType()));
        attr.setType(Attr.Type.getByValue(dataObject.getType()));
        return attr;
    }
}
