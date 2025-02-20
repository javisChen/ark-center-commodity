package com.ark.center.product.app.goods.service;

import com.ark.center.product.app.goods.query.GoodsQryExe;
import com.ark.center.product.client.goods.dto.GoodsDTO;
import com.ark.center.product.client.goods.mq.GoodsChangedEventDTO;
import com.ark.center.product.infra.product.repository.es.GoodsEsSynchronizer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品服务实现类
 * </p>
 *
 * @author EOP
 * @since 2022-03-05
 */
@Service
@RequiredArgsConstructor
public class GoodsCommandHandler {

    private final GoodsQryExe goodsQryExe;

    private final GoodsEsSynchronizer goodsEsSynchronizer;

    /**
     *
     * @param eventDTO
     */
    public void handleGoodsChangedEvent(GoodsChangedEventDTO eventDTO) {

        Long spuId = eventDTO.getSpuId();

        GoodsDTO goodsDTO = goodsQryExe.queryDetails(spuId);

        goodsEsSynchronizer.handleGoodsChangedEvent(goodsDTO);

    }
}
