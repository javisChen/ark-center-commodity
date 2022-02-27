package com.kt.cloud.commodity.dao.entity;

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
 * @since 2022-02-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("brand")
public class Brand extends BaseEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 品牌名称
     */
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

    /**
     * 删除标识 0-表示未删除 大于0-已删除
     */
    private Long isDeleted;

}
