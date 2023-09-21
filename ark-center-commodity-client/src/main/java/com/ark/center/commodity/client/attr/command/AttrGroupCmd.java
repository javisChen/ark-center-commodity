package com.ark.center.commodity.client.attr.command;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
/**
 * <p>
 * 商品属性组
 * </p>
 *
 * @author EOP
 * @since 2022-03-05
 */
@Data
@Schema(name = "AttrGroupCreateReqDTO对象", description = "商品属性组")
public class AttrGroupCmd implements Serializable {

    @Schema(name = "id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long id;
    @Schema(name = "属性分组名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "属性分组名称不能为空")
    private String name;
    @Schema(name = "模板ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "模板ID不能为空")
    private Long attrTemplateId;

}
