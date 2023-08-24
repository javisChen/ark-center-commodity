package com.ark.center.commodity.client.attr.command;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Schema(name = "AttrGroupUpdateReqDTO对象", description = "商品属性组")
public class AttrGroupUpdateCmd implements Serializable {

    @Schema(name = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "id不能为空")
    private Long id;

    @Schema(name = "属性分组名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "属性分组名称不能为空")
    private String name;

    @Schema(name = "模板ID，关联co_attr_template.id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "模板ID，关联co_attr_template.id不能为空")
    private Long attrTemplateId;

}