package com.ark.center.product.app.goods.query;

import com.ark.center.product.client.goods.dto.SkuDTO;
import com.ark.center.product.client.goods.query.SkuQry;
import com.ark.center.product.infra.product.convertor.SkuAppConvertor;
import com.ark.center.product.infra.sku.Sku;
import com.ark.center.product.infra.sku.SkuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SkuListQryExe {

    private final SkuAppConvertor skuAppConvertor;

    private final SkuService skuService;

    public List<SkuDTO> execute(SkuQry qry) {
        List<Sku> skus = skuService.queryByIds(qry.getSkuIds());
        return skuAppConvertor.convertSkuToDTO(skus);
    }

}