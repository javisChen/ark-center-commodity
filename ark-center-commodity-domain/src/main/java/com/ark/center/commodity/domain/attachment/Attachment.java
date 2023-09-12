package com.ark.center.commodity.domain.attachment;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ark.component.orm.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 附件表
 * </p>
 *
 * @author EOP
 * @since 2022-04-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("co_attachment")
public class Attachment extends BaseEntity {


    /**
     * 业务类型
     */
    @TableField("biz_type")
    private String bizType;

    /**
     * 业务ID
     */
    @TableField("biz_id")
    private Long bizId;

    /**
     * 附件地址
     */
    @TableField("url")
    private String url;

}
