package com.ark.center.commodity.domain.attr.assembler;


import com.ark.center.commodity.client.attr.command.AttrCreateCmd;
import com.ark.center.commodity.client.attr.dto.AttrDTO;
import com.ark.center.commodity.domain.attr.Attr;
import com.ark.component.dto.PageResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AttrAssembler {

    Attr toAttr(AttrCreateCmd command);

    PageResponse<AttrDTO> toDTO(IPage<Attr> page);

    AttrDTO toDTO(Attr attr);
}














