package com.ark.center.commodity.app.commodity.query;

import com.ark.center.commodity.client.commodity.dto.SkuDTO;
import com.ark.center.commodity.client.commodity.query.SkuQry;
import com.ark.center.commodity.domain.commodity.repository.SkuRepository;
import com.ark.center.commodity.domain.commodity.vo.Sku;
import com.ark.center.commodity.infra.commodity.convertor.SkuAppConvertor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SkuListQryExe {

    private final SkuAppConvertor skuAppConvertor;

    private final SkuRepository skuRepository;

    public List<SkuDTO> execute(SkuQry qry) {
        List<Sku> skus = skuRepository.queryByIds(qry.getSkuIds());
        return skuAppConvertor.convertSkuToDTO(skus);
    }

}