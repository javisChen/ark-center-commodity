package com.ark.center.product.client.search.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "商品搜索结果", description = "商品搜索结果")
public class GoodsSearchDTO {

    private List<SkuDTO> skus;

    private List<AggDTO> agg;
}
