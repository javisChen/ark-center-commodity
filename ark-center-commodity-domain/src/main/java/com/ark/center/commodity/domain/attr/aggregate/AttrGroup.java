package com.ark.center.commodity.domain.attr.aggregate;

import lombok.Data;

/**
 * <p>
 * 商品属性组
 * </p>
 *
 * @author EOP
 * @since 2022-03-08
 */
@Data
public class AttrGroup {

    private Long id;

    private String name;

    private Long attrTemplateId;

}
