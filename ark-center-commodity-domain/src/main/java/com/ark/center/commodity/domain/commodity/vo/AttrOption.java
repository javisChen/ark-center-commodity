package com.ark.center.commodity.domain.commodity.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class AttrOption {

    private Long attrId;

    private List<String> valueList;

}
