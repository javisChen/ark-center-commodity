package com.ark.center.commodity.domain.spu.gateway;

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

    void saveAttrs(List<SpuAttr> spuAttrs);

    void saveAttrOptions(List<AttrOption> dos);

    Long saveSpu(Spu spu);
}
