package com.ark.center.product.infra.spu.assembler;

import com.ark.center.product.client.goods.command.AttrCmd;
import com.ark.center.product.client.goods.command.GoodsCmd;
import com.ark.center.product.client.goods.dto.GoodsAttrDTO;
import com.ark.center.product.client.goods.dto.GoodsDTO;
import com.ark.center.product.infra.spu.Spu;
import com.ark.center.product.infra.spu.SpuAttr;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SpuAssembler {

    @Mapping(target = "pcRichText", ignore = true)
    @Mapping(target = "mobileRichText", ignore = true)
    @Mapping(target = "specs", ignore = true)
    @Mapping(target = "skus", ignore = true)
    @Mapping(target = "pictures", ignore = true)
    @Mapping(target = "params", ignore = true)
    @Mapping(target = "freightTemplateId", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "categoryName", ignore = true)
    @Mapping(target = "categoryLevelPath", ignore = true)
    @Mapping(target = "brandName", ignore = true)
    GoodsDTO toGoodsDTO(Spu spu);

    List<GoodsDTO> toGoodsDTO(List<Spu> spu);

    @Mapping(target = "verifyStatus", ignore = true)
    @Mapping(target = "sales", ignore = true)
    @Mapping(target = "modifier", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "creator", ignore = true)
    Spu toSpu(GoodsCmd cmd);

    List<SpuAttr> toSpuAttr(List<AttrCmd> attrCmd);

    List<GoodsAttrDTO> toGoodsAttr(List<SpuAttr> params);

}
