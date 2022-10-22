package com.ark.center.commodity.domain.attr.assembler;


import com.ark.center.commodity.client.attr.command.AttrGroupCreateCmd;
import com.ark.center.commodity.client.attr.command.AttrGroupUpdateCmd;
import com.ark.center.commodity.client.attr.dto.AttrTemplateDTO;
import com.ark.center.commodity.domain.attr.aggregate.AttrGroup;
import com.ark.component.web.util.bean.BeanConvertor;
import com.ark.ddd.base.BaseAssembler;
import org.springframework.stereotype.Component;

@Component
public class AttrGroupAssembler extends BaseAssembler<AttrGroup, AttrTemplateDTO> {

    public AttrGroup updateCmdToAggregate(AttrGroupUpdateCmd command) {
        return BeanConvertor.copy(command, AttrGroup.class);
    }

    public AttrGroup createCmdToAggregate(AttrGroupCreateCmd command) {
        return BeanConvertor.copy(command, AttrGroup.class);
    }

}














