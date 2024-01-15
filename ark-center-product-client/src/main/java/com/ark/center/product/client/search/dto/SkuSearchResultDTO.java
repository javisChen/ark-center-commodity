package com.ark.center.product.client.search.dto;

import lombok.Data;

import java.util.List;

@Data
public class SkuSearchResultDTO {

    List<SkuSearchDTO> skus;

    /**
     * 聚合信息
     */
    List<AggDTO> agg;
}
