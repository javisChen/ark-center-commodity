package com.kt.cloud.commodity.module.category.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.kt.component.dto.PagingQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * <p>
 * 商品类目
 * </p>
 *
 * @author EOP
 * @since 2022-03-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CategoryPageQueryReqDTO对象", description = "商品类目")
public class CategoryPageQueryReqDTO extends PagingQuery {


    @ApiModelProperty(value = "分类名称")
    private String name;

    @ApiModelProperty(value = "商品数量")
    private Integer commodityCount;

    @ApiModelProperty(value = "是否显示 enums[NO,否,0;YES,是,1]")
    private Integer isShow;

    @ApiModelProperty(value = "是否导航 enums[NO,否,0;YES,是,1]")
    private Integer isNav;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "上级ID")
    private Integer parentId;

    @ApiModelProperty(value = "规格参数模板ID")
    private Integer attrTemplateId;

    @ApiModelProperty(value = "删除标识 0-表示未删除 大于0-已删除")
    private Long isDeleted;

}
