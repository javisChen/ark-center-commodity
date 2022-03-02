package com.kt.cloud.commodity.module.category.dto.request;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotEmpty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * <p>
 * 商品类目
 * </p>
 *
 * @author EOP
 * @since 2022-03-02
 */
@Data
@ApiModel(value="CategoryUpdateReqDTO对象", description="商品类目")
public class CategoryUpdateReqDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "分类名称", required = true)
    @NotEmpty(message = "分类名称不能为空")
    private String name;

    @ApiModelProperty(value = "商品数量", required = false)
    private Integer commodityNum;

    @ApiModelProperty(value = "是否显示 enums[NO,否,0;YES,是,1]", required = true)
    @NotEmpty(message = "是否显示 enums[NO,否,0;YES,是,1]不能为空")
    private Boolean isShow;

    @ApiModelProperty(value = "是否导航 enums[NO,否,0;YES,是,1]", required = true)
    @NotEmpty(message = "是否导航 enums[NO,否,0;YES,是,1]不能为空")
    private Boolean isMenu;

    @ApiModelProperty(value = "排序", required = true)
    @NotNull(message = "排序不能为空")
    private Integer seq;

    @ApiModelProperty(value = "上级ID", required = true)
    @NotNull(message = "上级ID不能为空")
    private Integer parentId;

    @ApiModelProperty(value = "模板ID", required = true)
    @NotNull(message = "模板ID不能为空")
    private Integer templateId;

    @ApiModelProperty(value = "删除标识 0-表示未删除 大于0-已删除", required = true)
    @NotEmpty(message = "删除标识 0-表示未删除 大于0-已删除不能为空")
    private Long isDeleted;

}