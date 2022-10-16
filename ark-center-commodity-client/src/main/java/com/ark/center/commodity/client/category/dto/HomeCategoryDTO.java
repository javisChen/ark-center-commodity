package com.ark.center.commodity.client.category.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "HomeCategoryDTO", description = "HomeCategoryDTO")
public class HomeCategoryDTO implements Serializable {

    @ApiModelProperty(value = "分类id", required = true)
    private Long categoryId;

    @ApiModelProperty(value = "分类名称", required = true)
    private String categoryName;

    @ApiModelProperty(value = "类目下的商品", required = true)
    private List<SubCommodity> commodities;

    @Data
    public static class SubCommodity {

        private Long spuId;

        private String spuName;

        private String picUrl;
    }

}
