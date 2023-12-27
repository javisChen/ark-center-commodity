package com.ark.center.product.client.goods.command;

import com.ark.component.validator.ValidateGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@Schema(name = "GoodsCmd", description = "商品保存对象")
public class GoodsCmd implements Serializable {

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

    @Schema(name = "品牌id，关联brand.id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "品牌id，关联brand.id不能为空")
    private Long brandId;

    @Schema(name = "分类id，关联category.id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "分类id，关联category.id不能为空")
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
    private String pcRichText;

    @Schema(name = "移动端详情富文本", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String mobileRichText;

    @Schema(name = "SKU集合", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<SkuCmd> skus;

    @Schema(name = "参数列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<AttrCmd> params;

    @Schema(name = "属性项列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<AttrOptionCmd> newAttrOptionList;

    @Schema(name = "图片地址列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> pictures;

//    @Schema(name = "重置SKU标识，为true的话将会清空当前SPU的所有SKU；false=否，true=是", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
//    private Boolean resetSku = false;

}
