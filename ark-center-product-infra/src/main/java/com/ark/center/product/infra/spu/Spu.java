package com.ark.center.product.infra.spu;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ark.component.orm.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.ark.component.common.enums.BasicEnums;
import com.ark.component.common.enums.EnumUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * spu主表
 * </p>
 *
 * @author EOP
 * @since 2022-03-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pro_spu")
public class Spu extends BaseEntity {

    /**
     * 商品名称
     */
    @TableField("name")
    private String name;

    /**
     * 商品编号
     */
    @TableField("code")
    private String code;

    /**
     * 商品介绍
     */
    @TableField("description")
    private String description;

    /**
     * spu主图url
     */
    @TableField("main_picture")
    private String mainPicture;

    /**
     * 上下架状态：enums[DOWN,下架,0;UP,上架,1]
     */
    @TableField("shelf_status")
    private Integer shelfStatus;

    /**
     * 审核状态：enums[NO_CHECK,未审核,0;CHECK_PASS,审核通过,1;CHECK_NO_PASS,审核不通过,2]
     */
    @TableField("verify_status")
    private Integer verifyStatus;

    /**
     * 销量
     */
    @TableField("sales")
    private Integer sales;

    /**
     * 单位
     */
    @TableField("unit")
    private Integer unit;

    /**
     * 商品重量，默认为克(g)
     */
    @TableField("weight")
    private Integer weight;


    /**
     * 品牌id，关联brand.id
     */
    @TableField("brand_id")
    private Long brandId;

    /**
     * 分类id，关联category.id
     */
    @TableField("category_id")
    private Long categoryId;

    @Getter
    @AllArgsConstructor
    public enum VerifyStatus implements BasicEnums {
        NO_CHECK(0, "未审核"),
        CHECK_PASS(1, "审核通过"),
        CHECK_NO_PASS(2, "审核不通过"),
        ;
        private final Integer value;
        private final String text;

        public static VerifyStatus getByValue(Integer value) {
            return EnumUtils.getByValue(values(), value);
        }

        public static String getText(Integer value) {
            return EnumUtils.getTextByValue(values(), value);
        }
    }
}
