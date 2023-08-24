package com.ark.center.commodity.client.attr.query;

import com.ark.component.dto.PagingQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * <p>
 * 商品属性
 * </p>
 *
 * @author EOP
 * @since 2022-03-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(name = "AttrPageQueryReqDTO对象", description = "商品属性")
public class AttrPageQry extends PagingQuery {

    @Schema(name = "属性ID")
    private Long id;

    @Schema(name = "属性名称")
    private String name;

    @Schema(name = "属性录入方式，enums[INPUT,手工录入,1;SELECT,从选项列表选取,2]")
    private Integer inputType;

    @Schema(name = "属性的类型，enums[SPEC,规格,1;PARAM,参数,2]")
    private Integer type;

    @Schema(name = "排序")
    private Integer sort;

    @Schema(name = "模板ID")
    private Long attrTemplateId;

    @Schema(name = "商品类别ID")
    private Long categoryId;

    @Schema(name = "是否查询属性可选项列表，false=否 true=是")
    private Boolean withOptions = false;

    @Schema(name = "商品ID")
    private Long spuId;

}
