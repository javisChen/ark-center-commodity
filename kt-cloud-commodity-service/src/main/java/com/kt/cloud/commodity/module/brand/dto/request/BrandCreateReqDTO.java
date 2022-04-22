package com.kt.cloud.commodity.module.brand.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BrandCreateReqDTO {

    @ApiModelProperty(value = "品牌名称", required = true)
    @NotBlank(message = "品牌名称不能为空")
    private String name;

    @ApiModelProperty(value = "品牌图片地址", required = true)
    @NotBlank(message = "品牌图片地址不能为空")
    private String imageUrl;

    @ApiModelProperty(value = "首字母", required = true)
    @NotBlank(message = "首字母不能为空")
    private String letter;

}
