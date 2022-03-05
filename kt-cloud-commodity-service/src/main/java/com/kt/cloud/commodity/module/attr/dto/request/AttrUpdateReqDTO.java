package com.kt.cloud.commodity.module.attr.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
/**
 * <p>
 * 商品属性
 * </p>
 *
 * @author EOP
 * @since 2022-03-05
 */
@Data
@ApiModel(value = "AttrUpdateReqDTO对象", description = "商品属性")
public class AttrUpdateReqDTO implements Serializable {

    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "属性名称", required = true)
    @NotEmpty(message = "属性名称不能为空")
    private String name;

    @ApiModelProperty(value = "属性录入方式，enums[INPUT,手工录入,1;SELECT,从选项列表选取,2]", required = true)
    @NotNull(message = "属性录入方式不能为空")
    private Integer inputType;

    @ApiModelProperty(value = "选项列表，有多个选项以逗号（,）分隔", required = true)
    @NotEmpty(message = "选项列表不能为空，有多个选项以逗号（,）分隔")
    private String options;

    @ApiModelProperty(value = "属性的类型，enums[SPEC,规格,1;PARAM,参数,2]", required = true)
    @NotNull(message = "属性的类型不能为空")
    private Integer type;

    @ApiModelProperty(value = "排序", required = false)
    private Integer sort;

    @ApiModelProperty(value = "模板ID，关联co_attr_template.id", required = true)
    @NotEmpty(message = "模板ID，关联co_attr_template.id不能为空")
    private Long attrTemplateId;

    @ApiModelProperty(value = "模板ID，关联co_attr_template.id", required = true)
    @NotNull(message = "属性模板分组ID不能为空")
    private Long attrGroupId;

    @ApiModelProperty(value = "是否支持手动新增，enums[NO,不支持,0;YES,支持,1]", required = false)
    private Integer canManualAdd;

}