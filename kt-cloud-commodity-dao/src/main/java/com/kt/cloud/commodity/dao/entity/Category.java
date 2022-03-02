package com.kt.cloud.commodity.dao.entity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.kt.component.db.base.BaseEntity;
import java.io.Serializable;
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
 * @since 2022-03-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Category extends BaseEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 分类名称
     */
    @TableField("`name`")
    private String name;

    /**
     * 商品数量
     */
    private Integer commodityNum;

    /**
     * 是否显示 enums[NO,否,0;YES,是,1]
     */
    private Boolean isShow;

    /**
     * 是否导航 enums[NO,否,0;YES,是,1]
     */
    private Boolean isMenu;

    /**
     * 排序
     */
    private Integer seq;

    /**
     * 上级ID
     */
    private Integer parentId;

    /**
     * 模板ID
     */
    private Integer templateId;

    /**
     * 删除标识 0-表示未删除 大于0-已删除
     */
    private Long isDeleted;

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
