package com.ark.center.product.client.attr.query;

import com.ark.component.dto.PagingQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * <p>
 * 商品属性模板
 * </p>
 *
 * @author EOP
 * @since 2022-03-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(name = "AttrTemplatePageQueryReqDTO对象", description = "商品属性模板")
public class AttrTemplatePageQry extends PagingQuery {


    @Schema(name = "模板名称")
    private String name;

}
