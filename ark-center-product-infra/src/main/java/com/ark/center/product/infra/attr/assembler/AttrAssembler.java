package com.ark.center.product.infra.attr.assembler;


import com.ark.center.product.client.attr.command.AttrCreateCmd;
import com.ark.center.product.client.attr.dto.AttrDTO;
import com.ark.center.product.infra.attr.Attr;
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














