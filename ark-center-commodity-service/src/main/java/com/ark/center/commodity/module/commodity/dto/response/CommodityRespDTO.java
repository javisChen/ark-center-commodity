package com.ark.center.commodity.module.commodity.dto.response;

import com.ark.center.commodity.api.sku.response.SkuAttrRespDTO;
import com.ark.center.commodity.api.sku.response.SkuRespDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * CommodityPageRespDTO
 * </p>
 *
 * @author EOP
 * @since 2022-03-05
 */
@Data
@ApiModel(value = "CommodityPageRespDTO对象", description = "CommodityPageRespDTO")
public class CommodityRespDTO implements Serializable {

    @ApiModelProperty(value = "商品名称", required = false)
    private Long id;

    @ApiModelProperty(value = "商品名称", required = true)
    private String name;

    @ApiModelProperty(value = "商品编号", required = true)
    private String code;

    @ApiModelProperty(value = "商品介绍", required = false)
    private String description;

    @ApiModelProperty(value = "品牌id，关联co_brand.id", required = true)
    private Long brandId;

    @ApiModelProperty(value = "分类id，关联co_category.id", required = true)
    private Long categoryId;

    @ApiModelProperty(value = "分类等级路径", required = true)
    private String categoryLevelPath;

    @ApiModelProperty(value = "spu主图url", required = false)
    private String mainPicture;

    @ApiModelProperty(value = "上下架状态：enums[DOWN,下架,0;UP,上架,1]", required = true)
    private Integer shelfStatus = 1;

    @ApiModelProperty(value = "默认展示价格（单位：分）", required = true)
    private Integer showPrice;

    @ApiModelProperty(value = "单位（G、KG）", required = true)
    private Integer unit;

    @ApiModelProperty(value = "商品重量，默认为克(g)", required = true)
    private Integer weight;

    @ApiModelProperty(value = "运费模板ID", required = false)
    private Long freightTemplateId;

    @ApiModelProperty(value = "PC端详情富文本", required = false)
    private String pcDetailHtml;

    @ApiModelProperty(value = "移动端详情富文本", required = false)
    private String mobileDetailHtml;

    @ApiModelProperty(value = "SKU列表", required = true)
    private List<SkuRespDTO> skuList;

    @ApiModelProperty(value = "参数列表", required = true)
    private List<SkuAttrRespDTO> paramList;

    @ApiModelProperty(value = "图片地址列表", required = true)
    private List<String> picList;

}
