package com.kt.cloud.commodity.module.spu.dto.response;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * spu主表
 * </p>
 *
 * @author EOP
 * @since 2022-03-05
 */
@Data
@ApiModel(value = "SpuRespDTO对象", description = "spu主表")
public class SpuRespDTO implements Serializable {


    @ApiModelProperty(value = "商品名称", required = true)
    private String mainName;

    @ApiModelProperty(value = "spu主图url", required = true)
    private String mainPicture;

    @ApiModelProperty(value = "上下架状态：enums[DOWN,下架,0;UP,上架,1]", required = true)
    private Integer shelfStatus;

    @ApiModelProperty(value = "审核状态：enums[NO_CHECK,未审核,0;CHECK_PASS,审核通过,1;CHECK_NO_PASS,审核不通过,2]", required = true)
    private Integer verifyStatus;

    @ApiModelProperty(value = "销量", required = true)
    private Integer sales;

    @ApiModelProperty(value = "默认展示价格（单位：分）", required = true)
    private Integer showPrice;

    @ApiModelProperty(value = "单位", required = false)
    private String unit;

    @ApiModelProperty(value = "商品重量，默认为克(g)", required = false)
    private Integer weight;

    @ApiModelProperty(value = "排序", required = true)
    private Integer sort;

    @ApiModelProperty(value = "品牌id，关联co_brand.id", required = true)
    private Long brandId;

    @ApiModelProperty(value = "分类id，关联co_category.id", required = true)
    private Long categoryId;

}
