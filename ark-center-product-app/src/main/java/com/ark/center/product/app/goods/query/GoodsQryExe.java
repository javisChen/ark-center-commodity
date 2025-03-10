package com.ark.center.product.app.goods.query;

import cn.hutool.core.lang.Assert;
import com.ark.center.product.client.goods.dto.GoodsDTO;
import com.ark.center.product.client.goods.query.GoodsQry;
import com.ark.center.product.infra.spu.Spu;
import com.ark.center.product.infra.product.service.SpuService;
import com.ark.center.product.infra.product.builder.GoodsBuildProfiles;
import com.ark.center.product.infra.product.builder.GoodsBuilder;
import com.ark.component.dto.PageResponse;
import com.ark.component.exception.ExceptionFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GoodsQryExe {

    private final SpuService spuService;

    private final GoodsBuilder goodsBuilder;

    public PageResponse<GoodsDTO> queryPages(GoodsQry goodsQry) {
        Page<Spu> pages = spuService.selectPages(goodsQry);
        List<Spu> records = pages.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            return PageResponse.of(new Page<>(pages.getCurrent(), pages.getSize()));
        }

        GoodsBuildProfiles profiles = new GoodsBuildProfiles();
        profiles.setWithPictures(true);
        profiles.setWithSaleInfo(false);

        List<GoodsDTO> goods = goodsBuilder.build(pages.getRecords(), profiles);
        return PageResponse.of(pages.getCurrent(), pages.getSize(), pages.getTotal(), goods);
    }

    public GoodsDTO queryDetails(Long spuId) {
        Spu spu = spuService.selectById(spuId);
        Assert.notNull(spu, () -> ExceptionFactory.userException("商品不存在"));

        GoodsBuildProfiles profiles = new GoodsBuildProfiles();
        return goodsBuilder.build(spu, profiles);
    }

}