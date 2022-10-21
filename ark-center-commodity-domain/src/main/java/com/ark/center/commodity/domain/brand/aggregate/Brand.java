package com.ark.center.commodity.domain.brand.aggregate;

import lombok.Data;


@Data
public class Brand {

    private Long id;


    private String name;


    private String imageUrl;


    private String letter;


    private Integer sort;
}
