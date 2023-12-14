package com.ark.center.product.client.goods.dto;

import com.ark.center.product.client.attr.dto.AttrOptionDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 规格属性DTO
 * @author jc
 */
@Data
public class AttrDTO {

    @Schema(name = "spuId", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long spuId;
    @Schema(name = "属性Id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long attrId;
    @Schema(name = "属性名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String attrName;
    @Schema(name = "属性值", requiredMode = Schema.RequiredMode.REQUIRED)
    private String attrValue;
    @Schema(name = "属性可选项列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<AttrOptionDTO> optionList;
}
