package com.ark.center.product.client.category.command;

import com.ark.component.validator.ValidateGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
/**
 * <p>
 * 商品类目
 * </p>
 *
 * @author EOP
 * @since 2022-03-03
 */
@Data
@Schema(name = "CategoryUpdateCommand", description = "CategoryUpdateCommand")
public class CategoryUpdateCmd implements Serializable {

    @Schema(name = "分类id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "分类id不能为空", groups = ValidateGroup.Update.class)
    private Long id;

    @Schema(name = "分类名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "分类名称不能为空")
    private String name;

    @Schema(name = "是否显示 enums[NO,否,0;YES,是,1]", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否显示 enums[NO,否,0;YES,是,1]不能为空")
    private Integer isShow;

    @Schema(name = "是否导航 enums[NO,否,0;YES,是,1]", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否导航 enums[NO,否,0;YES,是,1]不能为空")
    private Integer isNav;

    @Schema(name = "排序", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer sort;

    @Schema(name = "上级ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long pid;

    @Schema(name = "规格参数模板ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long attrTemplateId;

}