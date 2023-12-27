package com.ark.center.product.client.goods.query;

import com.ark.component.dto.PagingQuery;
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
@Schema(name = "ProductQry", description = "商品查询")
public class GoodsQry extends PagingQuery {

    @Schema(name = "SpuId，关联spu.id")
    private Long spuId;

    @Schema(name = "spu编码")
    private String sn;

}
