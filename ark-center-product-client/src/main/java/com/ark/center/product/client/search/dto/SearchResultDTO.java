package com.ark.center.product.client.search.dto;

import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Data
public class SearchResultDTO {

    private List<SkuSearchDTO> skus = Collections.emptyList();

    private List<AggDTO> agg = Collections.emptyList();

}
