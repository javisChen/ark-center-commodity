package com.ark.center.commodity.domain.attr.assembler;


import com.ark.center.commodity.client.attr.command.AttrTemplateCreateCmd;
import com.ark.center.commodity.client.attr.command.AttrTemplateUpdateCmd;
import com.ark.center.commodity.client.attr.dto.AttrTemplateDTO;
import com.ark.center.commodity.domain.attr.aggregate.AttrTemplate;
import com.ark.component.web.util.bean.BeanConvertor;
import com.ark.ddd.base.BaseAssembler;
import org.springframework.stereotype.Component;

@Component
public class AttrTemplateAssembler extends BaseAssembler<AttrTemplate, AttrTemplateDTO> {

    public AttrTemplate updateCmdToAggregate(AttrTemplateUpdateCmd command) {
        return BeanConvertor.copy(command, AttrTemplate.class);
    }

    public AttrTemplate createCmdToAggregate(AttrTemplateCreateCmd command) {
        return BeanConvertor.copy(command, AttrTemplate.class);
    }

}














