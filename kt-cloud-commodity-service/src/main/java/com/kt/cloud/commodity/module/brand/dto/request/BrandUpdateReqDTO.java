package com.kt.cloud.commodity.module.brand.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BrandUpdateReqDTO {

    @ApiModelProperty(value = "品牌id", required = true)
    @NotBlank(message = "品牌id不能为空")
    private Long id;

    @ApiModelProperty(value = "品牌名称", required = false)
    @NotBlank(message = "品牌名称不能为空")
    private String name;

    @ApiModelProperty(value = "品牌图片地址", required = false)
    @NotBlank(message = "品牌图片地址不能为空")
    private String imageUrl;

    @ApiModelProperty(value = "首字母", required = false)
    @NotBlank(message = "首字母不能为空")
    private String letter;

}
