package com.ark.center.commodity.module.commodity.dto.request;

import com.ark.component.validator.ValidateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "CommodityUpdateReqDTO对象", description = "商品对象")
public class CommodityUpdateReqDTO implements Serializable {

    @ApiModelProperty(value = "商品名称", required = false)
    @NotBlank(message = "商品ID不能为空", groups = ValidateGroup.Update.class)
    private Long id;

    @ApiModelProperty(value = "商品名称", required = true)
    @NotBlank(message = "商品名称不能为空")
    private String name;

    @ApiModelProperty(value = "商品编号", required = true)
    @NotBlank(message = "商品编号不能为空")
    private String code;

    @ApiModelProperty(value = "商品介绍", required = false)
    private String description;

    @ApiModelProperty(value = "品牌id，关联co_brand.id", required = true)
    @NotNull(message = "品牌id，关联co_brand.id不能为空")
    private Long brandId;

    @ApiModelProperty(value = "分类id，关联co_category.id", required = true)
    @NotNull(message = "分类id，关联co_category.id不能为空")
    private Long categoryId;

    @ApiModelProperty(value = "spu主图url", required = false)
    private String mainPicture;

    @ApiModelProperty(value = "上下架状态：enums[DOWN,下架,0;UP,上架,1]", required = true)
    private Integer shelfStatus = 1;

    @ApiModelProperty(value = "默认展示价格（单位：分）", required = true)
    @NotNull(message = "默认展示价格）不能为空")
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
    private List<SkuUpdateReqDTO> skuList;

    @ApiModelProperty(value = "参数列表", required = true)
    private List<AttrReqDTO> paramList;

    @ApiModelProperty(value = "属性项列表", required = true)
    private List<AttrOptionReqDTO> newAttrOptionList;

    @ApiModelProperty(value = "图片地址列表", required = true)
    private List<String> picList;

    @ApiModelProperty(value = "刷新SKU标记", required = false)
    private Boolean flushSku = false;

}
