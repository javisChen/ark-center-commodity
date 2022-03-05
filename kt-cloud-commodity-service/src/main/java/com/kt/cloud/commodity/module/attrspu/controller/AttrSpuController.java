package com.kt.cloud.commodity.module.attrspu.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kt.cloud.commodity.module.attrspu.dto.request.AttrSpuCreateReqDTO;
import com.kt.cloud.commodity.module.attrspu.dto.request.AttrSpuUpdateReqDTO;
import com.kt.cloud.commodity.module.attrspu.dto.request.AttrSpuPageQueryReqDTO;
import com.kt.cloud.commodity.module.attrspu.dto.response.AttrSpuRespDTO;
import com.kt.cloud.commodity.module.attrspu.dto.response.AttrSpuRespDTO;
import com.kt.cloud.commodity.module.attrspu.service.AttrSpuService;
import com.kt.component.dto.PageResponse;
import com.kt.component.dto.SingleResponse;
import com.kt.component.web.util.bean.BeanConvertor;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;
import com.kt.component.web.base.BaseController;

/**
 * <p>
 * SPU特有的商品属性选项 前端控制器
 * </p>
 *
 * @author EOP
 * @since 2022-03-05
 */
@Api(tags = "SPU特有的商品属性选项")
@Validated
@RestController
@RequestMapping("/v1/attr-spu")
public class AttrSpuController extends BaseController {

    private final AttrSpuService attrSpuService;

    public AttrSpuController(AttrSpuService attrSpuService) {
        this.attrSpuService = attrSpuService;
    }

    @ApiOperation(value = "创建")
    @PostMapping("/create")
    public SingleResponse<Long> create(@RequestBody @Validated AttrSpuCreateReqDTO reqDTO) {
        return SingleResponse.ok(attrSpuService.createAttrSpu(reqDTO));
    }

    @ApiOperation(value = "修改")
    @PostMapping("/update")
    public SingleResponse<Long> update(@RequestBody @Validated AttrSpuUpdateReqDTO reqDTO) {
        return SingleResponse.ok(attrSpuService.updateAttrSpu(reqDTO));
    }

    @ApiOperation(value = "查询分页列表")
    @PostMapping("/page")
    public SingleResponse<PageResponse<AttrSpuRespDTO>> pageList(@RequestBody @Validated AttrSpuPageQueryReqDTO queryDTO) {
        return SingleResponse.ok(attrSpuService.getPageList(queryDTO));
    }

    @ApiOperation(value = "查询详情")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @GetMapping("/info")
    public SingleResponse<AttrSpuRespDTO> info(@RequestParam(required = false) @NotNull(message = "id不能为空") Long id) {
        return SingleResponse.ok(attrSpuService.getAttrSpuInfo(id));
    }


}
