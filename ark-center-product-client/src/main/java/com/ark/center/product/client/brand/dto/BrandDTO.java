package com.ark.center.product.client.brand.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BrandDTO {

    @Schema(name = "品牌ID")
    private Long id;

    @Schema(name = "品牌名称")
    private String name;

    @Schema(name = "品牌图片地址")
    private String imageUrl;

    @Schema(name = "首字母")
    private String letter;

    @Schema(name = "排序")
    private Integer sort;

    @Schema(name = "创建时间")
    private LocalDateTime gmtCreate;

}
