package com.ark.center.commodity.client.commodity.dto;

import com.ark.center.commodity.client.attr.dto.AttrOptionDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 规格属性DTO
 * @author jc
 */
@Data
public class AttrDTO {

    @Schema(name = "属性Id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long attrId;
    @Schema(name = "属性名称", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String attrName;
    @Schema(name = "属性值", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String attrValue;
    @Schema(name = "属性可选项列表", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<AttrOptionDTO> optionList;
}
