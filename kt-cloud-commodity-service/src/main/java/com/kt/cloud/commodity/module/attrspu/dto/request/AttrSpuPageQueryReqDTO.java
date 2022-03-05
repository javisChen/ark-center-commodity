package com.kt.cloud.commodity.module.attrspu.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.kt.component.dto.PagingQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * <p>
 * SPU特有的商品属性选项
 * </p>
 *
 * @author EOP
 * @since 2022-03-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AttrSpuPageQueryReqDTO对象", description = "SPU特有的商品属性选项")
public class AttrSpuPageQueryReqDTO extends PagingQuery {


    @ApiModelProperty(value = "商品属性ID，关联co_attr.id")
    private Long attrId;

    @ApiModelProperty(value = "SPU ID，关联co_spu.id")
    private Long spuId;

    @ApiModelProperty(value = "选项列表，有多个选项以逗号（,）分隔")
    private String options;

}
