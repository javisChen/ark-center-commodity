package com.ark.center.commodity.client.category.dto;

import lombok.Data;

@Data
public class TreeifyDTO {

    private String name;

    private Long id;

    private Integer sort;

    private Long pid;

    private Integer level;
}
