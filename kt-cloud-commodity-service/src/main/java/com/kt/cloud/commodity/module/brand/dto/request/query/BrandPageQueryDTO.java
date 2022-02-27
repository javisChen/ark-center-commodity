package com.kt.cloud.commodity.module.brand.dto.request.query;

import com.kt.component.dto.PagingQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class BrandPageQueryDTO extends PagingQuery implements Serializable {

    @ApiModelProperty(value = "品牌名称（支持全模糊查询）")
    private String name;

}
