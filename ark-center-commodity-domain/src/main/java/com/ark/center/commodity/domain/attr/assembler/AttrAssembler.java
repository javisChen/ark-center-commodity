package com.ark.center.commodity.domain.attr.assembler;


import com.ark.center.commodity.client.attr.command.AttrSaveCmd;
import com.ark.center.commodity.client.attr.command.AttrUpdateCmd;
import com.ark.center.commodity.client.attr.dto.AttrDTO;
import com.ark.center.commodity.client.attr.dto.AttrOptionDTO;
import com.ark.center.commodity.domain.attr.aggregate.Attr;
import com.ark.center.commodity.domain.attr.vo.AttrOption;
import com.ark.component.dto.PageResponse;
import com.ark.component.web.util.bean.BeanConvertor;
import com.ark.ddd.base.BaseAssembler;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AttrAssembler extends BaseAssembler<Attr, AttrDTO> {

    public Attr updateCmdToAggregate(AttrUpdateCmd command) {
        return BeanConvertor.copy(command, Attr.class);
    }

    public Attr createCmdToAggregate(AttrSaveCmd command) {
        Attr attr = BeanConvertor.copy(command, Attr.class);
        attr.setType(Attr.Type.getByValue(command.getType()));
        attr.setInputType(Attr.InputType.getByValue(command.getInputType()));
        attr.setCanManualAdd(command.getCanManualAdd().equals(1));
        return attr;
    }

    @Override
    public PageResponse<AttrDTO> toDTO(IPage<Attr> page) {
        IPage<AttrDTO> dto = page.convert(attr -> {
            AttrDTO attrDTO = super.toDTO(attr);
            attrDTO.setInputType(attr.getInputType().getValue());
            attrDTO.setType(attr.getType().getValue());
            List<AttrOption> options = attr.getOptions();
            if (CollectionUtils.isNotEmpty(options)) {
                attrDTO.setOptionList(options.stream()
                        .map(option -> BeanConvertor.copy(option, AttrOptionDTO.class))
                        .collect(Collectors.toList()));
            }
            return attrDTO;
        });
        return BeanConvertor.copyPage(dto, AttrDTO.class);
    }

    @Override
    public AttrDTO toDTO(Attr attr) {
        AttrDTO attrDTO = super.toDTO(attr);
        attrDTO.setInputType(attr.getInputType().getValue());
        attrDTO.setType(attr.getType().getValue());
        return attrDTO;
    }
}













