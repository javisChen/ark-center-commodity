package com.ark.center.product.client.attr.command;

import com.ark.component.validator.ValidateGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
/**
 * <p>
 * 商品属性
 * </p>
 *
 * @author EOP
 * @since 2022-03-08
 */
@Data
@Schema(name = "AttrUpdateReqDTO对象", description = "商品属性")
public class AttrUpdateCmd implements Serializable {

    @Schema(name = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "id不能为空", groups = ValidateGroup.Update.class)
    private Long id;

    @Schema(name = "属性名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "属性名称不能为空")
    private String name;

    @Schema(name = "属性录入方式，enums[INPUT,手工录入,1;SELECT,从选项列表选取,2]", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "属性录入方式，enums[INPUT,手工录入,1;SELECT,从选项列表选取,2]不能为空")
    private Integer inputType;

    @Schema(name = "属性的类型，enums[SPEC,规格,1;PARAM,参数,2]", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "属性的类型，enums[SPEC,规格,1;PARAM,参数,2]不能为空")
    private Integer type;

    @Schema(name = "排序", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "排序不能为空")
    private Integer sort;

    @Schema(name = "模板ID，关联attr_template.id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "模板ID，关联attr_template.id不能为空")
    private Long attrTemplateId;

    @Schema(name = "是否支持手动新增，enums[NO,不支持,0;YES,支持,1]", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否支持手动新增，enums[NO,不支持,0;YES,支持,1]不能为空")
    private Integer canManualAdd;

    @Schema(name = "属性可选值列表", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<String> values;

    @Schema(name = "属性组ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long attrGroupId;

}