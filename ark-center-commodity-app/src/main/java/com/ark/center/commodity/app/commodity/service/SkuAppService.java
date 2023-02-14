package com.ark.center.commodity.app.commodity.service;

import com.ark.center.commodity.app.commodity.executor.SkuStockReduceCmdExe;
import com.ark.center.commodity.app.commodity.query.SkuListQryExe;
import com.ark.center.commodity.client.commodity.command.SkuStockReduceCmd;
import com.ark.center.commodity.client.commodity.dto.SkuDTO;
import com.ark.center.commodity.client.commodity.query.SkuQry;
import com.ark.component.dto.ServerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
public class SkuAppService {

    private final SkuListQryExe skuListQryExe;
    private final SkuStockReduceCmdExe skuStockReduceCmdExe;

    public List<SkuDTO> querySkuList(SkuQry qry) {
        return skuListQryExe.execute(qry);
    }

    @Transactional(rollbackFor = Throwable.class)
    public ServerResponse decreaseStock(List<SkuStockReduceCmd> cmd) {
        skuStockReduceCmdExe.execute(cmd);
        return ServerResponse.ok();
    }
}
