package com.ark.center.commodity.domain.attr.assembler;


import com.ark.center.commodity.client.attr.command.AttrCreateCmd;
import com.ark.center.commodity.client.attr.command.AttrUpdateCmd;
import com.ark.center.commodity.client.attr.dto.AttrDTO;
import com.ark.center.commodity.domain.attr.Attr;
import com.ark.component.dto.PageResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AttrAssembler {

    public Attr updateCmdToAggregate(AttrUpdateCmd command);

    public Attr toAttr(AttrCreateCmd command);

    public PageResponse<AttrDTO> toDTO(IPage<Attr> page);
//        IPage<AttrDTO> dto = page.convert(attr -> {
//            AttrDTO attrDTO = super.toDTO(attr);
//            attrDTO.setInputType(attr.getInputType().getValue());
//            attrDTO.setType(attr.getType().getValue());
//            List<AttrOption> options = attr.getOptions();
//            if (CollectionUtils.isNotEmpty(options)) {
//                attrDTO.setOptionList(options.stream()
//                        .map(option -> BeanConvertor.copy(option, AttrOptionDTO.class))
//                        .collect(Collectors.toList()));
//            }
//            return attrDTO;
//        });
//        return BeanConvertor.copyPage(dto, AttrDTO.class);
//    }

    public AttrDTO toDTO(Attr attr);
}














