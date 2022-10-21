package com.ark.center.commodity.client.brand.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BrandDTO {

    @ApiModelProperty(value = "品牌ID")
    private Long id;

    @ApiModelProperty(value = "品牌名称")
    private String name;

    @ApiModelProperty(value = "品牌图片地址")
    private String imageUrl;

    @ApiModelProperty(value = "首字母")
    private String letter;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime gmtCreate;

}
