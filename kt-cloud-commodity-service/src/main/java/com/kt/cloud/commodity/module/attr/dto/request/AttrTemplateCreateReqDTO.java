package com.kt.cloud.commodity.module.attr.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
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
public class AttrTemplateCreateReqDTO implements Serializable {

    @ApiModelProperty(value = "模板名称", required = true)
    @NotEmpty(message = "模板名称不能为空")
    private String name;

}
