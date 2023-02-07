package com.ark.center.commodity.application.commodity.service;

import com.ark.center.commodity.application.commodity.query.SkuListQryExe;
import com.ark.center.commodity.client.commodity.dto.SkuDTO;
import com.ark.center.commodity.client.commodity.query.SkuQry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public List<SkuDTO> querySkuList(SkuQry qry) {
        return skuListQryExe.execute(qry);
    }
}
