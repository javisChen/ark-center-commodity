//package com.ark.center.commodity.module.attr.controller;
//
//import com.ark.center.commodity.module.attr.dto.request.AttrTemplateCreateReqDTO;
//import com.ark.center.commodity.module.attr.dto.request.AttrTemplatePageQueryReqDTO;
//import com.ark.center.commodity.module.attr.dto.request.AttrTemplateUpdateReqDTO;
//import com.ark.center.commodity.module.attr.dto.response.AttrTemplateRespDTO;
//import com.ark.center.commodity.module.attr.service.AttrTemplateService;
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
//import jakarta.validation.constraints.NotNull;
//
///**
// * <p>
// * 商品属性模板 前端控制器
// * </p>
// *
// * @author EOP
// * @since 2022-03-05
// */
//@Api(tags = "商品属性模板")
//@Validated
//@RestController
//@RequestMapping("/v1/attr/template")
//@RequiredArgsConstructor
//public class AttrTemplateController extends BaseController {
//    private final AttrTemplateService attrTemplateService;
//    @ApiOperation(value = "创建属性模板")
//    @PostMapping("/create")
//    public SingleResponse<Long> create(@RequestBody @Validated AttrTemplateCreateReqDTO reqDTO) {
//        return SingleResponse.ok(attrTemplateService.createAttrTemplate(reqDTO));
//    }
//
//    @ApiOperation(value = "编辑属性模板")
//    @PostMapping("/update")
//    public SingleResponse<Long> update(@RequestBody @Validated AttrTemplateUpdateReqDTO reqDTO) {
//        return SingleResponse.ok(attrTemplateService.updateAttrTemplate(reqDTO));
//    }
//
//    @ApiOperation(value = "查询属性模板分页列表")
//    @PostMapping("/page")
//    public SingleResponse<PageResponse<AttrTemplateRespDTO>> pageList(@RequestBody @Validated AttrTemplatePageQueryReqDTO queryDTO) {
//        return SingleResponse.ok(attrTemplateService.getPageList(queryDTO));
//    }
//
//    @ApiOperation(value = "查询属性模板详情")
//    @ApiImplicitParam(name = "id", value = "id", required = true)
//    @GetMapping("/info")
//    public SingleResponse<AttrTemplateRespDTO> info(@RequestParam(required = false) @NotNull(message = "id不能为空") Long id) {
//        return SingleResponse.ok(attrTemplateService.getAttrTemplateInfo(id));
//    }
//
//
//}
