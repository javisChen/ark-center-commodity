package com.ark.center.product.client.goods.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
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
@Schema(name = "GoodsDTO", description = "商品模型")
public class GoodsDTO implements Serializable {

    @Schema(name = "商品名称", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long id;

    @Schema(name = "商品名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(name = "商品编号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String code;

    @Schema(name = "商品介绍", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String description;

    @Schema(name = "品牌id，关联brand.id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long brandId;

    @Schema(name = "分类id，关联category.id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long categoryId;

    @Schema(name = "品牌名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String brandName;

    @Schema(name = "分类名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String categoryName;

    @Schema(name = "分类等级路径", requiredMode = Schema.RequiredMode.REQUIRED)
    private String categoryLevelPath;

    @Schema(name = "spu主图url", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String mainPicture;

    @Schema(name = "上下架状态：enums[DOWN,下架,0;UP,上架,1]", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer shelfStatus = 1;

    @Schema(name = "销售价格（单位：分）", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer salesPrice;

    @Schema(name = "单位（G、KG）", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer unit;

    @Schema(name = "商品重量，默认为克(g)", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer weight;

    @Schema(name = "运费模板ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long freightTemplateId;

    @Schema(name = "PC端详情富文本", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String pcRichText;

    @Schema(name = "移动端详情富文本", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String mobileRichText;

    @Schema(name = "SKU列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<SkuDTO> skus;

    @Schema(name = "参数列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<GoodsAttrDTO> params;

    @Schema(name = "图片地址列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> pictures;

    @Schema(name = "规格列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<GoodsAttrDTO> specs;

    @Schema(name = "商品创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    @Schema(name = "商品更新时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime updateTime;

}
