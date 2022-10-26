package com.ark.center.commodity.domain.commodity.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SaleInfo {


    /**
     * spuId，关联co_spu.id
     */
    private Long spuId;

    /**
     * 运费模版ID,关联freight_template.id
     */
    private Long freightTemplateId;

    /**
     * PC端商品介绍富文本
     */
    private String pcDetailHtml;

    /**
     * 移动端商品介绍富文本
     */
    private String mobileDetailHtml;

    /**
     * 参数属性JSON
     */
    private String paramData;

}
