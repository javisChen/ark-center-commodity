package com.ark.center.product.client.category.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 商品类目
 * </p>
 *
 * @author EOP
 * @since 2022-03-03
 */
@Data
@Schema(name = "树对象", description = "树型对象")
public class TreeDTO<T> implements Serializable {

    @Schema(name = "分类id", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Node<T>> nodes;

    @Data
    public static class Node<T> {
        private Long id;
        private String code;
        private String name;
        private Long pid;
        private List<Node<T>> nodes;

        public Node(Long id, String name, Long pid) {
            this.id = id;
            this.name = name;
            this.pid = pid;
        }

        public Node(Long id, String name, Long pid, String code) {
            this.id = id;
            this.name = name;
            this.pid = pid;
            this.code = code;
        }

    }
}
