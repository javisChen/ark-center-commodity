package com.ark.center.commodity.domain.commodity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Attr {

    private Long attrId;
    private String attrName;
    private String attrValue;

}
