package com.kt.cloud.commodity.module.attr.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 商品属性组
 * </p>
 *
 * @author EOP
 * @since 2022-03-05
 */
@Data
@ApiModel(value = "AttrGroupRespDTO对象", description = "商品属性组")
public class AttrGroupRespDTO implements Serializable {

    @ApiModelProperty(value = "id", required = true)
    private Long id;

    @ApiModelProperty(value = "属性分组名称", required = true)
    private String name;

    @ApiModelProperty(value = "模板ID，关联co_attr_template.id", required = true)
    private Long attrTemplateId;

}
