package com.ark.center.commodity.domain.spu.service;

import com.ark.center.commodity.domain.spu.Spu;
import com.ark.center.commodity.domain.spu.SpuAttr;
import com.ark.center.commodity.domain.spu.SpuSales;
import com.ark.center.commodity.domain.spu.gateway.SpuGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpuService {

    private final SpuGateway spuGateway;

}
