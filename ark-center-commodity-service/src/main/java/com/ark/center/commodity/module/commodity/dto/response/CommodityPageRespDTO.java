package com.ark.center.commodity.module.commodity.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "CommodityPageRespDTO对象", description = "CommodityPageRespDTO")
public class CommodityPageRespDTO implements Serializable {

    @ApiModelProperty(value = "商品ID", required = true)
    private Long id;

    @ApiModelProperty(value = "商品名称", required = true)
    private String name;

    @ApiModelProperty(value = "品牌名称", required = true)
    private String brandName;

    @ApiModelProperty(value = "分类名称", required = true)
    private String categoryName;

    @ApiModelProperty(value = "创建时间", required = true)
    private LocalDateTime gmtCreate;

    @ApiModelProperty(value = "上下架状态：enums[DOWN,下架,0;UP,上架,1]", required = true)
    private Integer shelfStatus;

}
