package com.ark.center.commodity.infrastructure.category.repository.db;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ark.component.orm.mybatis.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * <p>
 * 分类品牌关联表
 * </p>
 *
 * @author EOP
 * @since 2022-03-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("co_category_brand_rel")
public class CategoryBrandRelDO extends BaseEntity {

    /**
     * 分类ID
     */
    private Integer categoryId;

    /**
     * 品牌ID
     */
    private Integer brandId;

}
