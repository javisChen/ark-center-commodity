package com.ark.center.product.client.search.dto;


import lombok.Data;

import java.util.List;

@Data
public class SkuSearchDTO {

    private Long skuId;

    private Long spuId;

    private String skuName;

    private Long brandId;

    private Long categoryId;

    private Integer salesPrice;

    private List<String> pictures;

}
