package com.kt.cloud.commodity.module.category.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
@ApiModel(value = "CategoryUpdateReqDTO对象", description = "商品类目")
public class CategoryUpdateReqDTO implements Serializable {

    @ApiModelProperty(value = "分类id", required = true)
    @NotNull(message = "分类id不能为空")
    private Long id;

    @ApiModelProperty(value = "分类名称", required = true)
    @NotEmpty(message = "分类名称不能为空")
    private String name;

    @ApiModelProperty(value = "是否显示 enums[NO,否,0;YES,是,1]", required = true)
    @NotNull(message = "是否显示 enums[NO,否,0;YES,是,1]不能为空")
    private Integer isShow;

    @ApiModelProperty(value = "是否导航 enums[NO,否,0;YES,是,1]", required = true)
    @NotNull(message = "是否导航 enums[NO,否,0;YES,是,1]不能为空")
    private Integer isMenu;

    @ApiModelProperty(value = "排序", required = false)
    private Integer seq;

    @ApiModelProperty(value = "上级ID", required = false)
    private Integer parentId;

    @ApiModelProperty(value = "规格参数模板ID", required = false)
    private Integer specParamTemplateId;

}