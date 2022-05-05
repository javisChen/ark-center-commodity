package com.kt.cloud.commodity.module.commodity.support;

import com.kt.cloud.commodity.module.commodity.dto.request.CommodityUpdateReqDTO;

import java.util.Objects;

public class CommodityHelper {

    public static boolean isUpdateAction(CommodityUpdateReqDTO reqDTO) {
        return Objects.nonNull(reqDTO.getId()) && reqDTO.getId() > 0;
    }

}
