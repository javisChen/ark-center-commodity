package com.kt.cloud.commodity.module.brand.dto.request.cmd;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class BrandCreateReqDTO {

    @ApiModelProperty(value = "品牌名称", required = true)
    @NotEmpty(message = "品牌名称不能为空")
    private String name;

    /**
     * 品牌图片地址
     */
    @ApiModelProperty(value = "品牌图片地址", required = true)
    @NotEmpty(message = "品牌图片地址不能为空")
    private String imageUrl;

    /**
     * 品牌的首字母
     */
    @ApiModelProperty(value = "首字母", required = true)
    @NotEmpty(message = "首字母不能为空")
    private String letter;

}
