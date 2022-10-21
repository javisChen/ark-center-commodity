//package com.ark.center.commodity.module.brand.controller;
//
//import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.ark.center.commodity.module.brand.dto.request.BrandCreateReqDTO;
//import com.ark.center.commodity.module.brand.dto.request.BrandUpdateReqDTO;
//import com.ark.center.commodity.module.brand.dto.request.BrandPageQueryReqDTO;
//import com.ark.center.commodity.module.brand.dto.response.BrandRespDTO;
//import com.ark.center.commodity.module.brand.service.BrandService;
//import com.ark.component.dto.PageResponse;
//import com.ark.component.dto.SingleResponse;
//import com.ark.component.web.base.BaseController;
//import com.ark.component.web.util.bean.BeanConvertor;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiImplicitParam;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.constraints.NotNull;
//
//
//@Api(tags = "商品品牌")
//@Validated
//@RequestMapping("/v1/brand")
//@RestController
//public class BrandController extends BaseController {
//
//    private final BrandService brandService;
//
//    public BrandController(BrandService brandService) {
//        this.brandService = brandService;
//    }
//
//    @ApiOperation(value = "新建商品")
//    @PostMapping("/create")
//    public SingleResponse<Long> create(@RequestBody @Validated BrandCreateReqDTO reqDTO) {
//        return SingleResponse.ok(brandService.createBrand(reqDTO));
//    }
//
//    @ApiOperation(value = "修改商品")
//    @PostMapping("/update")
//    public SingleResponse<Long> update(@RequestBody @Validated BrandUpdateReqDTO reqDTO) {
//        return SingleResponse.ok(brandService.updateBrand(reqDTO));
//    }
//
//    @ApiOperation(value = "查询商品分页列表")
//    @PostMapping("/page")
//    public SingleResponse<PageResponse<BrandRespDTO>> pageList(@RequestBody @Validated BrandPageQueryReqDTO queryDTO) {
//        IPage<BrandRespDTO> page = brandService.getPageList(queryDTO);
//        return SingleResponse.ok(BeanConvertor.copyPage(page, BrandRespDTO.class));
//    }
//
//    @ApiOperation(value = "查询商品详情")
//    @ApiImplicitParam(name = "id", value = "id", required = true)
//    @GetMapping("/info")
//    public SingleResponse<BrandRespDTO> info(@RequestParam(required = false) @NotNull(message = "id不能为空") Long id) {
//        return SingleResponse.ok(brandService.getBrandInfo(id));
//    }
//
//}
