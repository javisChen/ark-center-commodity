package com.ark.center.commodity.module.attr.dto.request;

import com.ark.component.dto.PagingQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "AttrGroupPageQueryReqDTO对象", description = "商品属性组")
public class AttrGroupPageQueryReqDTO extends PagingQuery {

    @ApiModelProperty(value = "属性分组名称", required = false)
    private String name;

    @ApiModelProperty(value = "模板ID", required = false)
    private Long attrTemplateId;

    @ApiModelProperty(value = "商品类目ID", required = false)
    private Long categoryId;

    @ApiModelProperty(value = "查询属性组下的属性")
    private Boolean withAttr = false;

}
