package com.kt.cloud.commodity.module.spu.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.kt.component.dto.PagingQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * <p>
 * spu主表
 * </p>
 *
 * @author EOP
 * @since 2022-03-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SpuPageQueryReqDTO对象", description = "spu主表")
public class SpuPageQueryReqDTO extends PagingQuery {


    @ApiModelProperty(value = "商品名称")
    private String mainName;

    @ApiModelProperty(value = "spu主图url")
    private String mainPicture;

    @ApiModelProperty(value = "上下架状态：enums[DOWN,下架,0;UP,上架,1]")
    private Integer shelfStatus;

    @ApiModelProperty(value = "审核状态：enums[NO_CHECK,未审核,0;CHECK_PASS,审核通过,1;CHECK_NO_PASS,审核不通过,2]")
    private Integer verifyStatus;

    @ApiModelProperty(value = "销量")
    private Integer sales;

    @ApiModelProperty(value = "默认展示价格（单位：分）")
    private Integer showPrice;

    @ApiModelProperty(value = "单位")
    private String unit;

    @ApiModelProperty(value = "商品重量，默认为克(g)")
    private Integer weight;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "品牌id，关联co_brand.id")
    private Long brandId;

    @ApiModelProperty(value = "分类id，关联co_category.id")
    private Long categoryId;

}
