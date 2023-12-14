package com.ark.center.product.infra.product.convertor;

import com.ark.center.product.client.goods.command.GoodsCmd;
import com.ark.center.product.client.goods.dto.GoodsDTO;
import com.ark.center.product.domain.spu.Spu;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SpuConverter {

    @Mapping(target = "specs", ignore = true)
    @Mapping(target = "skus", ignore = true)
    @Mapping(target = "pictures", ignore = true)
    @Mapping(target = "pcDetailHtml", ignore = true)
    @Mapping(target = "params", ignore = true)
    @Mapping(target = "mobileDetailHtml", ignore = true)
    @Mapping(target = "freightTemplateId", ignore = true)
    @Mapping(target = "categoryLevelPath", ignore = true)
    GoodsDTO toGoodsDTO(Spu spu);

    List<GoodsDTO> toGoodsDTO(List<Spu> spu);

    @Mapping(target = "verifyStatus", ignore = true)
    @Mapping(target = "sales", ignore = true)
    @Mapping(target = "modifier", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "gmtModified", ignore = true)
    @Mapping(target = "gmtCreate", ignore = true)
    @Mapping(target = "creator", ignore = true)
    Spu toSpu(GoodsCmd cmd);

}
