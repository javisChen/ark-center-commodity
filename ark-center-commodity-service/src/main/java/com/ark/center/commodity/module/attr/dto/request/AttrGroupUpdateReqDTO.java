package com.ark.center.commodity.module.attr.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
@ApiModel(value = "AttrGroupUpdateReqDTO对象", description = "商品属性组")
public class AttrGroupUpdateReqDTO implements Serializable {

    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "属性分组名称", required = true)
    @NotBlank(message = "属性分组名称不能为空")
    private String name;

    @ApiModelProperty(value = "模板ID，关联co_attr_template.id", required = true)
    @NotNull(message = "模板ID，关联co_attr_template.id不能为空")
    private Long attrTemplateId;

}