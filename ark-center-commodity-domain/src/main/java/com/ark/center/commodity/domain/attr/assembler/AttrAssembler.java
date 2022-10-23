package com.ark.center.commodity.domain.attr.assembler;


import com.ark.center.commodity.client.attr.command.AttrSaveCmd;
import com.ark.center.commodity.client.attr.command.AttrUpdateCmd;
import com.ark.center.commodity.client.attr.dto.AttrDTO;
import com.ark.center.commodity.domain.attr.aggregate.Attr;
import com.ark.component.web.util.bean.BeanConvertor;
import com.ark.ddd.base.BaseAssembler;
import org.springframework.stereotype.Component;

@Component
public class AttrAssembler extends BaseAssembler<Attr, AttrDTO> {

    public Attr updateCmdToAggregate(AttrUpdateCmd command) {
        return BeanConvertor.copy(command, Attr.class);
    }

    public Attr createCmdToAggregate(AttrSaveCmd command) {
        Attr attr = BeanConvertor.copy(command, Attr.class);
        attr.setType(Attr.Type.getByValue(command.getInputType()));
        attr.setInputType(Attr.InputType.getByValue(command.getInputType()));
        attr.setCanManualAdd(command.getCanManualAdd().equals(1));
        return attr;
    }

}














