package com.ark.center.product.infra.spu.gateway;

import com.ark.center.product.client.goods.dto.GoodsAttrDTO;
import com.ark.center.product.client.goods.query.GoodsQry;
import com.ark.center.product.infra.attr.AttrOption;
import com.ark.center.product.infra.spu.Spu;
import com.ark.center.product.infra.spu.SpuAttr;
import com.ark.center.product.infra.spu.SpuSales;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

public interface SpuGateway {

    Spu selectById(Long id);

    Page<Spu> selectPages(GoodsQry queryDTO);

    void insert(Spu spu);

    void saveSpuSales(SpuSales spuSales);

    void insertAttrs(List<SpuAttr> spuAttrs);

    void saveAttrOptions(List<AttrOption> dos);

    void saveSpu(Spu spu);

    void updateSpu(Spu spu);

    List<SpuAttr> selectAttrsBySpuId(Long spuId);

    List<GoodsAttrDTO> selectSpecs(List<Long> spuIds);

    void batchDeleteAttrs(List<Long> records);

    SpuSales selectSalesBySpuId(Long spuId);

    List<SpuSales> selectSalesBySpuIds(List<Long> spuIds);

    List<Spu> selectByCategoryIds(List<Long> categoryIds);

    boolean updateSpuSales(SpuSales sales);
}
