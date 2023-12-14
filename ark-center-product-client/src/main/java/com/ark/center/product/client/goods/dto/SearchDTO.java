package com.ark.center.product.client.goods.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * SearchRespDTO
 * </p>
 *
 * @author EOP
 * @since 2022-03-05
 */
@Data
@Schema(name = "SearchRespDTO", description = "商品搜索结果")
public class SearchDTO implements Serializable {

    @Schema(name = "商品ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long skuId;
    @Schema(name = "商品名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String skuName;
    @Schema(name = "商品描述", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;
    @Schema(name = "商品展示图", requiredMode = Schema.RequiredMode.REQUIRED)
    private String picUrl;

}
