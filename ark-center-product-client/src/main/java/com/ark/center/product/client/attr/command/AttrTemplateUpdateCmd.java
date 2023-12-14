package com.ark.center.product.client.attr.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Schema(name = "AttrTemplateUpdateReqDTO对象", description = "商品属性模板")
public class AttrTemplateUpdateCmd implements Serializable {

    @Schema(name = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "id不能为空")
    private Long id;

    @Schema(name = "模板名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "模板名称不能为空")
    private String name;

}