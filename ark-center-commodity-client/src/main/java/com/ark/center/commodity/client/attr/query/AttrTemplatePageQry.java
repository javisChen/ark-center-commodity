package com.ark.center.commodity.client.attr.query;

import com.ark.component.dto.PagingQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * <p>
 * 商品属性模板
 * </p>
 *
 * @author EOP
 * @since 2022-03-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AttrTemplatePageQueryReqDTO对象", description = "商品属性模板")
public class AttrTemplatePageQry extends PagingQuery {


    @ApiModelProperty(value = "模板名称")
    private String name;

}
