package com.ark.center.product.client.goods.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * SkuDTO
 * </p>
 *
 * @author EOP
 * @since 2022-03-05
 */
@Data
@Schema(name = "SkuDTO", description = "SKU信息对象")
public class SkuDTO implements Serializable {

    @Schema(name = "skuId", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;
    @Schema(name = "spuId", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long spuId;
    @Schema(name = "商品重量，默认为克(g)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String code;
    @Schema(name = "商品名称", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String name;
    @Schema(name = "销售价（单位：分）", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer salesPrice;
    @Schema(name = "成本价（单位：分）", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer costPrice;
    @Schema(name = "库存", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer stock;
    @Schema(name = "预警库存", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer warnStock;
    @Schema(name = "图片地址", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String mainPicture;
    @Schema(name  = "SKU规格属性列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<AttrDTO> specs;

}
