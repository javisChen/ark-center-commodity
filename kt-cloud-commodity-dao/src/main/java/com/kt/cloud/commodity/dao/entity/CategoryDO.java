package com.kt.cloud.commodity.dao.entity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kt.component.orm.mybatis.base.BaseEntity;
import com.kt.component.common.enums.BasicEnums;
import com.kt.component.common.enums.EnumUtils;
import lombok.*;

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
@NoArgsConstructor
public class CategoryDO extends BaseEntity {

    /**
     * 分类名称
     */
    @TableField("`name`")
    private String name;

    /**
     * 商品数量
     */
    @TableField("commodity_count")
    private Integer commodityCount;

    /**
     * 是否显示 enums[NO,否,0;YES,是,1]
     */
    @TableField("is_show")
    private Integer isShow;

    /**
     * 是否导航 enums[NO,否,0;YES,是,1]
     */
    @TableField("is_nav")
    private Integer isNav;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 上级ID
     */
    @TableField("pid")
    private Long pid;

    /**
     * 分类级别
     */
    @TableField("level")
    private Integer level;

    /**
     * 分类级别路径，例如：0.1.2 代表该分类是三级分类，上级路由的id是1,再上级的路由id是0
     */
    @TableField("level_path")
    private String levelPath;

    /**
     * 规格参数模板ID
     */
    @TableField("attr_template_id")
    private Long attrTemplateId;

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
    public enum IsNav implements BasicEnums {
        NO(0, "否"),
        YES(1, "是"),
        ;
        private final Integer value;
        private final String text;

        public static IsNav getByValue(Integer value) {
            return EnumUtils.getByValue(values(), value);
        }

        public static String getText(Integer value) {
            return EnumUtils.getTextByValue(values(), value);
        }
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
    }
}
