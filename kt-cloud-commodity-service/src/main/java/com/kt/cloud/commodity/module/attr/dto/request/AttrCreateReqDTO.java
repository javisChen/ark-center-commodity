package com.kt.cloud.commodity.module.attr.dto.request;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotEmpty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * <p>
 * 商品属性
 * </p>
 *
 * @author EOP
 * @since 2022-03-08
 */
@Data
@ApiModel(value = "AttrCreateReqDTO对象", description = "商品属性")
public class AttrCreateReqDTO implements Serializable {



    @ApiModelProperty(value = "属性名称", required = true)
    @NotEmpty(message = "属性名称不能为空")
    private String name;

    @ApiModelProperty(value = "属性录入方式，enums[INPUT,手工录入,1;SELECT,从选项列表选取,2]", required = true)
    @NotNull(message = "属性录入方式，enums[INPUT,手工录入,1;SELECT,从选项列表选取,2]不能为空")
    private Integer inputType;

    @ApiModelProperty(value = "属性的类型，enums[SPEC,规格,1;PARAM,参数,2]", required = true)
    @NotNull(message = "属性的类型，enums[SPEC,规格,1;PARAM,参数,2]不能为空")
    private Integer type;

    @ApiModelProperty(value = "排序", required = true)
    @NotNull(message = "排序不能为空")
    private Integer sort;

    @ApiModelProperty(value = "模板ID，关联co_attr_template.id", required = true)
    @NotEmpty(message = "模板ID，关联co_attr_template.id不能为空")
    private Long attrTemplateId;

    @ApiModelProperty(value = "是否支持手动新增，enums[NO,不支持,0;YES,支持,1]", required = true)
    @NotNull(message = "是否支持手动新增，enums[NO,不支持,0;YES,支持,1]不能为空")
    private Integer canManualAdd;

}
