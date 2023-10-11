package com.ark.center.commodity.client.commodity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * CommodityPageRespDTO
 * </p>
 *
 * @author EOP
 * @since 2022-03-05
 */
@Data
@Schema(name = "CommodityPageDTO", description = "CommodityPageDTO")
public class CommodityPageDTO implements Serializable {

    @Schema(name = "商品ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(name = "商品名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(name = "商品介绍", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;

    @Schema(name = "品牌名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String brandName;

    @Schema(name = "分类名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String categoryName;

    @Schema(name = "品牌id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long brandId;

    @Schema(name = "分类id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long categoryId;

    @Schema(name = "展示价格", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer showPrice;

    @Schema(name = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime gmtCreate;

    @Schema(name = "上下架状态：enums[DOWN,下架,0;UP,上架,1]", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer shelfStatus;

}
