package com.ark.center.commodity.client.commodity.command;

import com.ark.component.validator.ValidateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 商品
 * </p>
 * @author EOP
 * @since 2022-03-05
 */
@Data
@Schema(name = "CommoditySaveCommand", description = "商品对象")
public class CommoditySaveCmd implements Serializable {

    @Schema(name = "商品名称", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @NotBlank(message = "商品ID不能为空", groups = ValidateGroup.Update.class)
    private Long id;

    @Schema(name = "商品名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "商品名称不能为空")
    private String name;

    @Schema(name = "商品编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "商品编号不能为空")
    private String code;

    @Schema(name = "商品介绍", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String description;

    @Schema(name = "品牌id，关联co_brand.id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "品牌id，关联co_brand.id不能为空")
    private Long brandId;

    @Schema(name = "分类id，关联co_category.id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "分类id，关联co_category.id不能为空")
    private Long categoryId;

    @Schema(name = "spu主图url", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String mainPicture;

    @Schema(name = "上下架状态：enums[DOWN,下架,0;UP,上架,1]", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer shelfStatus = 1;

    @Schema(name = "默认展示价格（单位：分）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "默认展示价格）不能为空")
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
    private List<SkuUpdateCmd> skuList;

    @Schema(name = "参数列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<AttrCmd> paramList;

    @Schema(name = "属性项列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<AttrOptionCmd> newAttrOptionList;

    @Schema(name = "图片地址列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> picList;

    @Schema(name = "刷新SKU标记", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Boolean flushSku = false;

}
