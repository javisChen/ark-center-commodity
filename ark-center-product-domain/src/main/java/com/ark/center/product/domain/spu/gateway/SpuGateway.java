package com.ark.center.product.domain.spu.gateway;

import com.ark.center.product.client.goods.dto.AttrDTO;
import com.ark.center.product.client.goods.query.GoodsQry;
import com.ark.center.product.domain.attr.AttrOption;
import com.ark.center.product.domain.spu.Spu;
import com.ark.center.product.domain.spu.SpuAttr;
import com.ark.center.product.domain.spu.SpuSales;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

public interface SpuGateway {

    Spu selectById(Long id);

    Page<Spu> selectPages(GoodsQry queryDTO);

    void insert(Spu spu);

    void saveSales(SpuSales spuSales);

    void insertAttrs(List<SpuAttr> spuAttrs);

    void saveAttrOptions(List<AttrOption> dos);

    void saveSpu(Spu spu);

    void updateSpu(Spu spu);

    List<SpuAttr> selectAttrsBySpuId(Long spuId);

    List<AttrDTO> selectSpecs(List<Long> spuIds);

    void batchDeleteAttrs(List<Long> records);

    SpuSales selectSalesBySpuId(Long spuId);

    List<SpuSales> selectSalesBySpuIds(List<Long> spuIds);

    List<Spu> selectByCategoryIds(List<Long> categoryIds);
}
