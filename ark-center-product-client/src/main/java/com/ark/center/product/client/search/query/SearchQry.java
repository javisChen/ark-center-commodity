package com.ark.center.product.client.search.query;

import com.ark.component.dto.PagingQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 封装搜索条件
 * keyword=小米&categoryId=888&
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "SearchQry", description = "搜索模型")
public class SearchQry extends PagingQuery {

    @Schema(name = "keyword", description = "关键字")
    private String keyword;

    @Schema(name = "priceRange", description = "价格区间，格式：min-max。例如100-、-200、100-200")
    private String priceRange;

    @Schema(name = "category", description = "分类id")
    private Long category;

    @Schema(name = "brand", description = "品牌id集合，以^分隔")
    private String brand;

    @Schema(name = "attrs",
            description = "规格，以^分隔每组规格，以_分隔规格id和value，以||分隔多个value，格式attrId_attrValueA||attrValueB^attrId_attrValue^attrId_attrValue")
    private String attrs;
}
