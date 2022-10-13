package com.ark.center.commodity.module.brand.dto.request;

import com.ark.component.dto.PagingQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class BrandPageQueryReqDTO extends PagingQuery implements Serializable {

    @ApiModelProperty(value = "品牌名称（支持全模糊查询）")
    private String name;

}
