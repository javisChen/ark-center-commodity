package com.kt.cloud.commodity.dao.entity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kt.component.db.base.BaseEntity;
import com.kt.component.common.enums.BasicEnums;
import com.kt.component.common.enums.EnumUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * <p>
 * 商品类目
 * </p>
 *
 * @author EOP
 * @since 2022-03-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("co_category")
public class CategoryDO extends BaseEntity {


    /**
     * 分类名称
     */
    @TableField("`name`")
    private String name;

    /**
     * 商品数量
     */
    @TableField("commodity_num")
    private Integer commodityNum;

    /**
     * 是否显示 enums[NO,否,0;YES,是,1]
     */
    @TableField("is_show")
    private Integer isShow;

    /**
     * 是否导航 enums[NO,否,0;YES,是,1]
     */
    @TableField("is_menu")
    private Integer isMenu;

    /**
     * 排序
     */
    @TableField("seq")
    private Integer seq;

    /**
     * 上级ID
     */
    @TableField("parent_id")
    private Integer parentId;

    /**
     * 规格参数模板ID
     */
    @TableField("spec_param_template_id")
    private Integer specParamTemplateId;

    @Getter
    @AllArgsConstructor
    public enum IsShow implements BasicEnums {
        NO(0, "否"),
            YES(1, "是"),
    ;
        private final Integer value;
        private final String text;

        public static IsShow getByValue(Integer value) {
            return EnumUtils.getByValue(values(), value);
        }

        public static String getText(Integer value) {
            return EnumUtils.getTextByValue(values(), value);
        }
    }
    @Getter
    @AllArgsConstructor
    public enum IsMenu implements BasicEnums {
        NO(0, "否"),
            YES(1, "是"),
    ;
        private final Integer value;
        private final String text;

        public static IsMenu getByValue(Integer value) {
            return EnumUtils.getByValue(values(), value);
        }

        public static String getText(Integer value) {
            return EnumUtils.getTextByValue(values(), value);
        }
    }
}
