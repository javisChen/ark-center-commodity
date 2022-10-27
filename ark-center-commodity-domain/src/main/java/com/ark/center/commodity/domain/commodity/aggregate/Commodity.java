package com.ark.center.commodity.domain.commodity.aggregate;

import com.ark.center.commodity.domain.commodity.vo.*;
import com.ark.component.common.enums.BasicEnums;
import com.ark.component.common.enums.EnumUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Commodity {

    private Long id;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品编号
     */
    private String code;

    /**
     * 商品介绍
     */
    private String description;

    /**
     * spu主图url
     */
    private String mainPicture;

    /**
     * 上下架状态：enums[DOWN,下架,0;UP,上架,1]
     */
    private ShelfStatus shelfStatus;

    /**
     * 审核状态：enums[NO_CHECK,未审核,0;CHECK_PASS,审核通过,1;CHECK_NO_PASS,审核不通过,2]
     */
    private VerifyStatus verifyStatus;

    /**
     * 销量
     */
    private Integer sales;

    /**
     * 默认展示价格（单位：分）
     */
    private Integer showPrice;

    /**
     * 单位
     */
    private Integer unit;

    /**
     * 商品重量，默认为克(g)
     */
    private Integer weight;

    /**
     * 品牌id，关联co_brand.id
     */
    private Long brandId;

    /**
     * 分类id，关联co_category.id
     */
    private Long categoryId;

    /**
     * 刷新SKU
     */
    private Boolean flushSku;

    /**
     * SKU
     */
    private List<Sku> skuList;

    /**
     * 属性项列表
     */
    private List<AttrOption> attrOptionList;

    /**
     * 销售信息
     */
    private SalesInfo salesInfo;

    /**
     * 商品图片
     */
    private List<Picture> picList;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    @Getter
    @AllArgsConstructor
    public enum ShelfStatus implements BasicEnums {
        DOWN(0, "下架"),
        UP(1, "上架");
        private final Integer value;
        private final String text;

        public static ShelfStatus getByValue(Integer value) {
            return EnumUtils.getByValue(values(), value);
        }

        public static String getText(Integer value) {
            return EnumUtils.getTextByValue(values(), value);
        }
    }

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
