package com.ark.center.commodity.api.sku.request;

import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

@Data
public class SkuInfoGetReqDTO {

    @Size(max = 1000, message = "每次限制获取1000条SKU信息")
    private List<Long> skuIds;
}
