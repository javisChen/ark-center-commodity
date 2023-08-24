package com.ark.center.commodity.client.commodity.dto;

import com.ark.center.commodity.client.attr.dto.AttrDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * CommodityPageRespDTO
 * </p>
 *
 * @author EOP
 * @since 2022-03-05
 */
@Schema(name = "AppCommodityRespDTO", description = "AppCommodityRespDTO")
public class AppCommodityDTO implements Serializable {

    @Schema(name = "商品名称", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long id;

    @Schema(name = "商品id", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(name = "商品介绍", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String description;

    @Schema(name = "spu主图url", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String mainPicture;

    @Schema(name = "PC端详情富文本", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String pcDetailHtml;

    @Schema(name = "移动端详情富文本", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String mobileDetailHtml;

    @Schema(name = "价格", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer price;

    @Schema(name = "SKU列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<SkuDTO> skuList;

    @Schema(name = "参数列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<SkuAttrDTO> paramList;

    @Schema(name = "图片地址列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> picList;

    @Schema(name = "规格列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private Collection<AttrDTO> attrList;

}
