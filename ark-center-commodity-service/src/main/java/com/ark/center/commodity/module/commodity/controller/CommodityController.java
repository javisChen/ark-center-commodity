package com.ark.center.commodity.module.commodity.controller;

import com.ark.center.commodity.module.commodity.dto.response.AppCommodityRespDTO;
import com.ark.center.commodity.module.commodity.service.CommodityService;
import com.ark.component.dto.SingleResponse;
import com.ark.component.web.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * sku 前端控制器
 * </p>
 *
 * @author EOP
 * @since 2022-03-05
 */
@Api(tags = "用户端-商品")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/commodity")
public class CommodityController extends BaseController {

//    private final CommodityService commodityService;
//    @ApiOperation(value = "查询详情")
//    @ApiImplicitParam(name = "id", value = "id", required = true)
//    @GetMapping("/info")
//    public SingleResponse<AppCommodityRespDTO> info(@RequestParam(required = false) @NotNull(message = "id不能为空") Long id) {
//        return SingleResponse.ok(commodityService.getInfo(id));
//    }

}
