package com.kt.cloud.commodity.module.brand.dto.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class BrandCreateReqDTO {

    /**
     * 品牌名称
     */
    @NotEmpty(message = "品牌名称不能为空")
    private String name;

    /**
     * 品牌图片地址
     */
    @NotEmpty(message = "品牌图片地址不能为空")
    private String imageUrl;

    /**
     * 品牌的首字母
     */
    @NotEmpty(message = "首字母不能为空")
    private String letter;

}
