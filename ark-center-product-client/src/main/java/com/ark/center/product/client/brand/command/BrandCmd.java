package com.ark.center.product.client.brand.command;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
public class BrandCmd {

    @Schema(name = "品牌id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @NotNull(message = "品牌id不能为空")
    private Long id;

    @Schema(name = "品牌名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "品牌名称不能为空")
    private String name;

    @Schema(name = "品牌图片地址", requiredMode = Schema.RequiredMode.REQUIRED)
    private String imageUrl;

    @Schema(name = "首字母", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "首字母不能为空")
    private String letter;

}
