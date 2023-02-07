//package com.ark.center.commodity.module.commodity.controller;
//
//import com.ark.center.commodity.client.commodity.dto.SkuDTO;
//import com.ark.center.commodity.client.commodity.rpc.SkuApi;
//import com.ark.center.commodity.client.commodity.query.SkuQry;
//import com.ark.center.commodity.client.commodity.dto.SkuRespDTO;
//import com.ark.center.commodity.module.commodity.service.SkuService;
//import com.ark.component.dto.MultiResponse;
//import io.swagger.annotations.Api;
//import lombok.AllArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
///**
// * <p>
// * sku 前端控制器
// * </p>
// *
// * @author EOP
// * @since 2022-03-05
// */
//@Api(tags = "商品")
//@RestController
//@AllArgsConstructor
//@RequestMapping("/v1/sku")
//public class SkuController implements SkuApi {
//
//    private final SkuService skuService;
//
//    @Override
//    public MultiResponse<SkuDTO> getSkuInfoList(SkuQry qry) {
////        List<SkuRespDTO> byIds = skuService.findByIds(qry);
////        return MultiResponse.ok(byIds);
//        return null;
//    }
//}
