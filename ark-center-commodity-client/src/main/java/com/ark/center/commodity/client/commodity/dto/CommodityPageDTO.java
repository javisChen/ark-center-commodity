package com.ark.center.commodity.client.commodity.dto;

import io.swagger.annotations.ApiModel;
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
@Schema(name = "CommodityPageRespDTO对象", description = "CommodityPageRespDTO")
public class CommodityPageDTO implements Serializable {

    @Schema(name = "商品ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(name = "商品名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(name = "品牌名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String brandName;

    @Schema(name = "分类名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String categoryName;

    @Schema(name = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime gmtCreate;

    @Schema(name = "上下架状态：enums[DOWN,下架,0;UP,上架,1]", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer shelfStatus;

}
