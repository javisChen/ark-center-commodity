package com.kt.cloud.commodity.module.spu.dto.request;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotEmpty;
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
@ApiModel(value = "SpuUpdateReqDTO对象", description = "spu主表")
public class SpuUpdateReqDTO implements Serializable {


    @ApiModelProperty(value = "商品名称", required = true)
    @NotEmpty(message = "商品名称不能为空")
    private String mainName;

    @ApiModelProperty(value = "spu主图url", required = true)
    @NotEmpty(message = "spu主图url不能为空")
    private String mainPicture;

    @ApiModelProperty(value = "上下架状态：enums[DOWN,下架,0;UP,上架,1]", required = true)
    @NotNull(message = "上下架状态：enums[DOWN,下架,0;UP,上架,1]不能为空")
    private Integer shelfStatus;

    @ApiModelProperty(value = "审核状态：enums[NO_CHECK,未审核,0;CHECK_PASS,审核通过,1;CHECK_NO_PASS,审核不通过,2]", required = true)
    @NotNull(message = "审核状态：enums[NO_CHECK,未审核,0;CHECK_PASS,审核通过,1;CHECK_NO_PASS,审核不通过,2]不能为空")
    private Integer verifyStatus;

    @ApiModelProperty(value = "销量", required = true)
    @NotNull(message = "销量不能为空")
    private Integer sales;

    @ApiModelProperty(value = "默认展示价格（单位：分）", required = true)
    @NotNull(message = "默认展示价格（单位：分）不能为空")
    private Integer showPrice;

    @ApiModelProperty(value = "单位", required = false)
    private String unit;

    @ApiModelProperty(value = "商品重量，默认为克(g)", required = false)
    private Integer weight;

    @ApiModelProperty(value = "排序", required = true)
    @NotNull(message = "排序不能为空")
    private Integer sort;

    @ApiModelProperty(value = "品牌id，关联co_brand.id", required = true)
    @NotEmpty(message = "品牌id，关联co_brand.id不能为空")
    private Long brandId;

    @ApiModelProperty(value = "分类id，关联co_category.id", required = true)
    @NotEmpty(message = "分类id，关联co_category.id不能为空")
    private Long categoryId;

}