package com.ark.center.commodity.domain.commodity.vo;

import lombok.AllArgsConstructor;

import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class Sku {

    private Long id;

    private String code;

    private Integer salesPrice;

    private Integer costPrice;

    private Integer stock;

    private Integer warnStock;

    private List<Attr> specList;


}
