package com.ark.center.product.client.search.dto;

import lombok.Data;

import java.util.List;

@Data
public class AggDTO {

    private Long id;

    private String name;

    private String type;

    private List<Option> options;

    @Data
    public static class Option {

        private String name;

        private String value;
    }
}
