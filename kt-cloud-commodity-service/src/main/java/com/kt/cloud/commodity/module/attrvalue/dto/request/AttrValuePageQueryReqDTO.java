package com.kt.cloud.commodity.module.attrvalue.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.kt.component.dto.PagingQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * <p>
 * 商品属性值
 * </p>
 *
 * @author EOP
 * @since 2022-03-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AttrValuePageQueryReqDTO对象", description = "商品属性值")
public class AttrValuePageQueryReqDTO extends PagingQuery {


    @ApiModelProperty(value = "商品属性ID，关联co_attr.id")
    private Long attrId;

    @ApiModelProperty(value = "选项值")
    private String value;

    @ApiModelProperty(value = "类型：enums[COMMON,通用,1;EXCLUSIVE,商品特有,2]")
    private Integer type;

}
