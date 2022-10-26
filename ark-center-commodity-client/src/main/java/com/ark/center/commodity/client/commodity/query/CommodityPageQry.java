package com.ark.center.commodity.client.commodity.query;

import com.ark.component.dto.PagingQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * <p>
 * sku
 * </p>
 *
 * @author EOP
 * @since 2022-03-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SkuPageQueryReqDTO对象", description = "sku")
public class CommodityPageQry extends PagingQuery {


    @ApiModelProperty(value = "SpuId，关联co_spu.id")
    private Long spuId;

    @ApiModelProperty(value = "spu编码")
    private String sn;

    @ApiModelProperty(value = "销售价（单位：分）")
    private Integer salesPrice;

    @ApiModelProperty(value = "成本价（单位：分）")
    private Integer costPrice;

    @ApiModelProperty(value = "库存")
    private Integer stock;

    @ApiModelProperty(value = "预警库存")
    private Integer warnStock;

    @ApiModelProperty(value = "规格参数JSON")
    private String specData;

}
