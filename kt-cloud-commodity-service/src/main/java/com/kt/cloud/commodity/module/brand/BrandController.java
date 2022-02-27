package com.kt.cloud.commodity.module.brand;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kt.cloud.commodity.module.brand.dto.request.cmd.BrandCreateReqDTO;
import com.kt.cloud.commodity.module.brand.dto.request.query.BrandPageQueryDTO;
import com.kt.cloud.commodity.module.brand.dto.response.BrandRespDTO;
import com.kt.cloud.commodity.module.brand.service.BrandService;
import com.kt.component.dto.PageResponse;
import com.kt.component.dto.SingleResponse;
import com.kt.component.web.util.bean.BeanConvertor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "商品管理")
@RequestMapping("/v1/brand")
@RestController
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @PostMapping("/create")
    @ApiOperation(value = "新建商品")
    public SingleResponse<Long> create(@RequestBody @Validated BrandCreateReqDTO reqDTO) {
        return SingleResponse.ok(brandService.create(reqDTO));
    }

    @PostMapping("/page")
    @ApiOperation(value = "商品分页列表")
    public PageResponse<BrandRespDTO> pageList(@RequestBody @Validated BrandPageQueryDTO queryDTO) {
        IPage<BrandRespDTO> page = brandService.pageList(queryDTO);
        return BeanConvertor.copyPage(page, BrandRespDTO.class);
    }
}
