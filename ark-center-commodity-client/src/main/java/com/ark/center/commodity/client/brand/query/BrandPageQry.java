package com.ark.center.commodity.client.brand.query;

import com.ark.component.dto.PagingQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class BrandPageQry extends PagingQuery implements Serializable {

    @Schema(name = "品牌名称（支持全模糊查询）")
    private String name;

}
