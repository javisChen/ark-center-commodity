package com.kt.cloud.commodity.module.brand.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BrandRespDTO {

    @ApiModelProperty(value = "品牌名称")
    private String name;

    @ApiModelProperty(value = "品牌图片地址")
    private String imageUrl;

    @ApiModelProperty(value = "首字母")
    private String letter;

    @ApiModelProperty(value = "排序")
    private Integer sort;

}
