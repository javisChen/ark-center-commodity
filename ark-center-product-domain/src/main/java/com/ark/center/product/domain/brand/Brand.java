package com.ark.center.product.domain.brand;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ark.component.orm.mybatis.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * <p>
 * 品牌表
 * </p>
 *
 * @author EOP
 * @since 2022-03-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("co_brand")
public class Brand extends BaseEntity {

    /**
     * 品牌名称
     */
    @TableField("`name`")
    private String name;

    /**
     * 品牌图片地址
     */
    @TableField("image_url")
    private String imageUrl;

    /**
     * 品牌的首字母
     */
    @TableField("letter")
    private String letter;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;

}
