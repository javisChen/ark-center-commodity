package com.ark.center.product.client.goods.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.Size;
import java.util.List;

@Data
public class SkuQry {

    @Size(max = 1000, message = "每次限制获取1000条SKU信息")
    @Schema(name = "skuId集合")
    private List<Long> skuIds;
}
