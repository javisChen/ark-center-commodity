package com.ark.center.commodity.client.commodity.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 属性选项值
 * @author jc
 */
@Data
public class AttrOptionCmd {

    @Schema(name = "属性Id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long attrId;
    @Schema(name = "属性项值", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<String> valueList;
}
