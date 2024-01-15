package com.ark.center.product.client.search.dto;

import lombok.Data;

import java.util.List;

@Data
public class SearchResultDTO {

    private List<SkuSearchDTO> skus;

    private List<AggDTO> agg;
}
