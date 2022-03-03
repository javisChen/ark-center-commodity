package com.kt.cloud.commodity.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kt.component.db.base.BaseEntity;
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
public class BrandDO extends BaseEntity {

    /**
     * 品牌名称
     */
    @TableField("`name`")
    private String name;

    /**
     * 品牌图片地址
     */
    private String imageUrl;

    /**
     * 品牌的首字母
     */
    private String letter;

    /**
     * 排序
     */
    private Integer seq;

}
