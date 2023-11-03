package com.ark.center.commodity.client.commodity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "CommodityDTO", description = "CommodityDTO")
public class CommodityDTO implements Serializable {

    @Schema(name = "商品名称", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long id;

    @Schema(name = "商品名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(name = "商品编号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String code;

    @Schema(name = "商品介绍", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String description;

    @Schema(name = "品牌id，关联co_brand.id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long brandId;

    @Schema(name = "分类id，关联co_category.id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long categoryId;

    @Schema(name = "分类等级路径", requiredMode = Schema.RequiredMode.REQUIRED)
    private String categoryLevelPath;

    @Schema(name = "spu主图url", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String mainPicture;

    @Schema(name = "上下架状态：enums[DOWN,下架,0;UP,上架,1]", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer shelfStatus = 1;

    @Schema(name = "默认展示价格（单位：分）", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer showPrice;

    @Schema(name = "单位（G、KG）", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer unit;

    @Schema(name = "商品重量，默认为克(g)", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer weight;

    @Schema(name = "运费模板ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long freightTemplateId;

    @Schema(name = "PC端详情富文本", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String pcDetailHtml;

    @Schema(name = "移动端详情富文本", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String mobileDetailHtml;

    @Schema(name = "SKU列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<SkuDTO> skuList;

    @Schema(name = "参数列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<AttrDTO> paramList;

    @Schema(name = "图片地址列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> picList;

    @Schema(name = "规格列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<AttrDTO> specList;

}
