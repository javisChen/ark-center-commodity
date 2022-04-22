package com.kt.cloud.commodity.module.category.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
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
@ApiModel(value = "CategoryCreateReqDTO对象", description = "商品类目")
public class CategoryCreateReqDTO implements Serializable {

    @ApiModelProperty(value = "分类名称", required = true)
    @NotBlank(message = "分类名称不能为空")
    private String name;

    @ApiModelProperty(value = "是否显示 enums[NO,否,0;YES,是,1]", required = true)
    @NotNull(message = "是否显示 enums[NO,否,0;YES,是,1]不能为空")
    private Integer isShow;

    @ApiModelProperty(value = "是否导航 enums[NO,否,0;YES,是,1]", required = true)
    @NotNull(message = "是否导航 enums[NO,否,0;YES,是,1]不能为空")
    private Integer isNav;

    @ApiModelProperty(value = "排序", required = false)
    private Integer sort;

    @ApiModelProperty(value = "上级ID", required = false)
    private Integer parentId;

    @ApiModelProperty(value = "规格参数模板ID", required = false)
    private Long attrTemplateId;

}
