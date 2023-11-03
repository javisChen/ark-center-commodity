package com.ark.center.commodity.domain.spu.gateway;

import com.ark.center.commodity.client.commodity.dto.AttrDTO;
import com.ark.center.commodity.client.commodity.dto.CommodityPageDTO;
import com.ark.center.commodity.client.commodity.query.CommodityPageQry;
import com.ark.center.commodity.domain.attr.AttrOption;
import com.ark.center.commodity.domain.spu.Spu;
import com.ark.center.commodity.domain.spu.SpuAttr;
import com.ark.center.commodity.domain.spu.SpuSales;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

public interface SpuGateway {

    Spu selectById(Long id);

    IPage<CommodityPageDTO> selectPages(CommodityPageQry queryDTO);

    void insert(Spu spu);

    void saveSales(SpuSales spuSales);

    void insertAttrs(List<SpuAttr> spuAttrs);

    void saveAttrOptions(List<AttrOption> dos);

    void saveSpu(Spu spu);

    void updateSpu(Spu spu);

    List<SpuAttr> selectAttrsBySpuId(Long spuId);

    List<AttrDTO> selectSpecs(Long spuId);

    void batchDeleteAttrs(List<Long> records);

    SpuSales selectSalesBySpuId(Long spuId);

    List<Spu> selectByCategoryIds(List<Long> categoryIds);
}
