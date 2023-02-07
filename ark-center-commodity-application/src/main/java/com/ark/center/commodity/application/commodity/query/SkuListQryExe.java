package com.ark.center.commodity.application.commodity.query;
import com.ark.center.commodity.application.commodity.convertor.SkuAppConvertor;
import com.ark.center.commodity.client.commodity.dto.SkuAttrDTO;
import com.ark.center.commodity.domain.commodity.vo.Attr;
import com.google.common.collect.Lists;

import com.ark.center.commodity.client.commodity.dto.SkuDTO;
import com.ark.center.commodity.client.commodity.query.SkuQry;
import com.ark.center.commodity.domain.commodity.repository.SkuRepository;
import com.ark.center.commodity.domain.commodity.vo.Sku;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SkuListQryExe {

    SkuAppConvertor INSTANCE = Mappers.getMapper(SkuAppConvertor.class);

    private final SkuRepository skuRepository;

    public List<SkuDTO> execute(SkuQry qry) {
        List<Sku> skus = skuRepository.queryByIds(qry.getSkuIds());
        return INSTANCE.convertSkuToDTO(skus);
    }

}