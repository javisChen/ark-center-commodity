package com.ark.center.commodity.client.commodity.query;

import com.ark.component.dto.PagingQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "SkuPageQueryReqDTO对象", description = "sku")
public class CommodityPageQry extends PagingQuery {


    @Schema(name = "SpuId，关联co_spu.id")
    private Long spuId;

    @Schema(name = "spu编码")
    private String sn;

    @Schema(name = "销售价（单位：分）")
    private Integer salesPrice;

    @Schema(name = "成本价（单位：分）")
    private Integer costPrice;

    @Schema(name = "库存")
    private Integer stock;

    @Schema(name = "预警库存")
    private Integer warnStock;

    @Schema(name = "规格参数JSON")
    private String specData;

}
