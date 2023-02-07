package com.ark.center.commodity.client.category.query;

import com.ark.component.dto.PagingQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "CategoryPageQueryReqDTO对象", description = "商品类目")
public class CategoryPageQry extends PagingQuery {

    @ApiModelProperty(value = "分类名称")
    private String name;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "分类等级")
    private Integer level = 1;

    @ApiModelProperty(value = "上级ID")
    private Long pid;

    @ApiModelProperty(value = "规格参数模板ID")
    private Long attrTemplateId;

}