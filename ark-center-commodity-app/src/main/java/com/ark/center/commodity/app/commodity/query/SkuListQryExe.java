package com.ark.center.commodity.app.commodity.query;

import com.ark.center.commodity.client.commodity.dto.SkuDTO;
import com.ark.center.commodity.client.commodity.query.SkuQry;
import com.ark.center.commodity.domain.spu.Sku;
import com.ark.center.commodity.domain.spu.gateway.SkuGateway;
import com.ark.center.commodity.infra.commodity.convertor.SkuAppConvertor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SkuListQryExe {

    private final SkuAppConvertor skuAppConvertor;

    private final SkuGateway skuGateway;

    public List<SkuDTO> execute(SkuQry qry) {
        List<Sku> skus = skuGateway.queryByIds(qry.getSkuIds());
        return skuAppConvertor.convertSkuToDTO(skus);
    }

}