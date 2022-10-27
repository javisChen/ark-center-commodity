//package com.ark.center.commodity.module.commodity.controller;
//
//import com.ark.center.commodity.module.commodity.dto.request.CommodityPageQueryReqDTO;
//import com.ark.center.commodity.module.commodity.dto.request.CommodityUpdateReqDTO;
//import com.ark.center.commodity.module.commodity.dto.response.CommodityPageRespDTO;
//import com.ark.center.commodity.module.commodity.dto.response.CommodityRespDTO;
//import com.ark.center.commodity.module.commodity.dto.response.SearchRespDTO;
//import com.ark.center.commodity.module.commodity.service.CommodityAdminService;
//import com.ark.component.dto.PageResponse;
//import com.ark.component.dto.SingleResponse;
//import com.ark.component.web.base.BaseController;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiImplicitParam;
//import io.swagger.annotations.ApiOperation;
//import lombok.RequiredArgsConstructor;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.constraints.NotNull;
//
///**
// * <p>
// * sku 前端控制器
// * </p>
// *
// * @author EOP
// * @since 2022-03-05
// */
//@Api(tags = "管理端-商品")
//@RestController
//@RequestMapping("/v1/admin/commodity")
//@RequiredArgsConstructor
//public class CommodityAdminController extends BaseController {
//    private final CommodityAdminService commodityAdminService;
//
//    @ApiOperation(value = "创建商品")
//    @PostMapping("/create")
//    public SingleResponse<Long> create(@RequestBody @Validated CommodityUpdateReqDTO reqDTO) {
//        return SingleResponse.ok(commodityAdminService.save(reqDTO));
//    }
//
//    @ApiOperation(value = "修改商品")
//    @PostMapping("/update")
//    public SingleResponse<Long> update(@RequestBody @Validated CommodityUpdateReqDTO reqDTO) {
//        return SingleResponse.ok(commodityAdminService.save(reqDTO));
//    }
//
//    @ApiOperation(value = "查询分页列表")
//    @PostMapping("/page")
//    public SingleResponse<PageResponse<CommodityPageRespDTO>> pageList(@RequestBody @Validated CommodityPageQueryReqDTO queryDTO) {
//        return SingleResponse.ok(commodityAdminService.getPageList(queryDTO));
//    }
//
//    @ApiOperation(value = "查询详情")
//    @ApiImplicitParam(name = "id", value = "id", required = true)
//    @GetMapping("/info")
//    public SingleResponse<CommodityRespDTO> info(@RequestParam(required = false) @NotNull(message = "id不能为空") Long id) {
//        return SingleResponse.ok(commodityAdminService.getInfo(id));
//    }
//
//    @ApiOperation(value = "查询分页列表")
//    @PostMapping("/search")
//    public SingleResponse<SearchRespDTO> pageList() {
//        return SingleResponse.ok(commodityAdminService.search());
//    }
//
//}
