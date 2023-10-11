package com.ark.center.commodity.client.category.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 商品类目
 * </p>
 *
 * @author EOP
 * @since 2022-03-03
 */
@Data
@Schema(name = "CategoryRespDTO对象", description = "商品类目")
public class CategoryDTO extends TreeifyDTO implements Serializable {

    @Schema(name = "分类id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(name = "分类名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(name = "分类编码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String code;

    @Schema(name = "商品数量", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer commodityCount;

    @Schema(name = "是否显示 enums[NO,否,0;YES,是,1]", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer isShow;

    @Schema(name = "是否导航 enums[NO,否,0;YES,是,1]", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer isNav;

    @Schema(name = "排序", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer sort;

    @Schema(name = "上级ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long pid;

    @Schema(name = "层级路径", requiredMode = Schema.RequiredMode.REQUIRED)
    private String levelPath;

    @Schema(name = "规格参数模板ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long attrTemplateId;

    @Schema(name = "分类级别", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer level;

    @Schema(name = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime gmtCreate;

}
