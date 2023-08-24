package com.ark.center.commodity.client.category.query;

import com.ark.component.dto.PagingQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * <p>
 * 商品类目
 * </p>
 *
 * @author EOP
 * @since 2022-03-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(name = "CategoryPageQueryReqDTO对象", description = "商品类目")
public class CategoryPageQry extends PagingQuery {

    @Schema(name = "分类名称")
    private String name;

    @Schema(name = "排序")
    private Integer sort;

    @Schema(name = "分类等级")
    private Integer level = 1;

    @Schema(name = "上级ID")
    private Long pid;

    @Schema(name = "规格参数模板ID")
    private Long attrTemplateId;

}
