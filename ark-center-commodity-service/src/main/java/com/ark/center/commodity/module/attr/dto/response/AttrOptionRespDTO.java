package com.ark.center.commodity.module.attr.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 商品可选项
 * </p>
 *
 * @author EOP
 * @since 2022-03-08
 */
@Data
@ApiModel(value = "AttrOptionRespDTO对象", description = "商品可选项")
public class AttrOptionRespDTO implements Serializable {

    @ApiModelProperty(value = "id", required = true)
    private Long id;

    @ApiModelProperty(value = "商品属性ID，关联co_attr.id", required = true)
    private Long attrId;

    @ApiModelProperty(value = "选项值内容", required = true)
    private String value;

    @ApiModelProperty(value = "类型：enums[COMMON,通用,1;EXCLUSIVE,商品特有,2]", required = true)
    private Integer type;

}