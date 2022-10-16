package com.ark.center.commodity.domain.category.aggregate;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductCategory {

    private Long productCategoryId;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 商品数量
     */
    private Integer count;

    /**
     * 是否显示 enums[NO,否,0;YES,是,1]
     */
    private Integer isShow;

    /**
     * 是否导航 enums[NO,否,0;YES,是,1]
     */
    private Integer isNav;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 上级ID
     */
    private Long pid;

    /**
     * 分类级别
     */
    private Integer level;

    /**
     * 分类级别路径，例如：0.1.2 代表该分类是三级分类，上级路由的id是1,再上级的路由id是0
     */
    private String levelPath;

    /**
     * 规格参数模板ID
     */
    private Long attrTemplateId;

    private LocalDateTime gmtCreate;

}
