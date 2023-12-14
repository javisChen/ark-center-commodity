package com.ark.center.product.client.attr.query;

import com.ark.component.dto.PagingQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 商品属性组
 * </p>
 *
 * @author EOP
 * @since 2022-03-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(name = "AttrGroupPageQueryReqDTO对象", description = "商品属性组")
public class AttrGroupPageQry extends PagingQuery {

    @Schema(name = "属性分组名称", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String name;

    @Schema(name = "模板ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long attrTemplateId;

    @Schema(name = "商品类目ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long categoryId;

    @Schema(name = "查询属性组下的属性", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Boolean withAttr = true;

}
