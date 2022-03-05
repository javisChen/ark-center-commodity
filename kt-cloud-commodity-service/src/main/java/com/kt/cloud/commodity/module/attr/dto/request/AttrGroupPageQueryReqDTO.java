package com.kt.cloud.commodity.module.attr.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.kt.component.dto.PagingQuery;
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


    @ApiModelProperty(value = "属性分组名称")
    private String name;

    @ApiModelProperty(value = "模板ID，关联co_attr_template.id")
    private Long attrTemplateId;

}
