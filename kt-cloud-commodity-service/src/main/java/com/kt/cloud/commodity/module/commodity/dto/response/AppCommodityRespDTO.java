package com.kt.cloud.commodity.module.commodity.dto.response;

import com.kt.cloud.commodity.api.sku.response.SkuAttrRespDTO;
import com.kt.cloud.commodity.api.sku.response.SkuRespDTO;
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
@ApiModel(value = "AppCommodityRespDTO", description = "AppCommodityRespDTO")
public class AppCommodityRespDTO implements Serializable {
    @ApiModelProperty(value = "商品名称", required = false)
    private Long id;
    @ApiModelProperty(value = "商品id", required = true)
    private String name;
    @ApiModelProperty(value = "商品介绍", required = false)
    private String description;
    @ApiModelProperty(value = "分类id，关联co_category.id", required = true)
    private Long categoryId;
    @ApiModelProperty(value = "分类等级路径", required = true)
    private String categoryLevelPath;
    @ApiModelProperty(value = "spu主图url", required = false)
    private String mainPicture;
    @ApiModelProperty(value = "上下架状态：enums[DOWN,下架,0;UP,上架,1]", required = true)
    private Integer shelfStatus = 1;

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
