package com.ark.center.product.client.category.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 商品类目
 * </p>
 *
 * @author EOP
 * @since 2022-03-03
 */
@Data
@Schema(name = "HomeCategoryDTO", description = "HomeCategoryDTO")
public class HomeCategoryDTO implements Serializable {

    @Schema(name = "分类id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long categoryId;

    @Schema(name = "分类名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String categoryName;

    @Schema(name = "类目下的商品", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<SubCommodity> commodities;

    @Data
    public static class SubCommodity {

        private Long spuId;

        private String spuName;

        private String picUrl;

    }

}
