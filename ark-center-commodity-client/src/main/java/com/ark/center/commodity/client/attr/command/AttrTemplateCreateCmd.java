package com.ark.center.commodity.client.attr.command;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
/**
 * <p>
 * 商品属性模板
 * </p>
 *
 * @author EOP
 * @since 2022-03-05
 */
@Data
@ApiModel(value = "AttrTemplateCreateReqDTO对象", description = "商品属性模板")
public class AttrTemplateCreateCmd implements Serializable {

    @ApiModelProperty(value = "模板名称", required = true)
    @NotBlank(message = "模板名称不能为空")
    private String name;

}
