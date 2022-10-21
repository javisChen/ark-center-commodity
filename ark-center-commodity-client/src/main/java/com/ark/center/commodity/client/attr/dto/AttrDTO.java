package com.ark.center.commodity.client.attr.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 商品属性
 * </p>
 *
 * @author EOP
 * @since 2022-03-08
 */
@Data
@ApiModel(value = "AttrRespDTO对象", description = "商品属性")
public class AttrDTO implements Serializable {

    @ApiModelProperty(value = "id", required = true)
    private Long id;

    @ApiModelProperty(value = "属性名称", required = true)
    private String name;

    @ApiModelProperty(value = "属性录入方式，enums[INPUT,手工录入,1;SELECT,从选项列表选取,2]", required = true)
    private Integer inputType;

    @ApiModelProperty(value = "属性的类型，enums[SPEC,规格,1;PARAM,参数,2]", required = true)
    private Integer type;

    @ApiModelProperty(value = "排序", required = true)
    private Integer sort;

    @ApiModelProperty(value = "模板ID，关联co_attr_template.id", required = true)
    private Long attrTemplateId;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime gmtCreate;

    @ApiModelProperty(value = "是否支持手动新增，enums[NO,不支持,0;YES,支持,1]", required = true)
    private Integer canManualAdd;

    @ApiModelProperty(value = "属性组ID", required = false)
    private Long attrGroupId;

    @ApiModelProperty(value = "属性可选项列表", required = false)
    private List<AttrOptionDTO> optionList;

}
