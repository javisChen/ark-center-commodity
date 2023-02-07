package com.ark.center.commodity.adapter.commodity.web;

import com.ark.center.commodity.application.category.service.CategoryAdminApplicationService;
import com.ark.center.commodity.application.commodity.service.SkuAppService;
import com.ark.center.commodity.client.category.command.CategoryCreateCmd;
import com.ark.center.commodity.client.category.command.CategoryUpdateCmd;
import com.ark.center.commodity.client.category.dto.CategoryDTO;
import com.ark.center.commodity.client.category.dto.TreeDTO;
import com.ark.center.commodity.client.category.query.CategoryPageQry;
import com.ark.center.commodity.client.commodity.dto.SkuDTO;
import com.ark.center.commodity.client.commodity.query.SkuQry;
import com.ark.center.commodity.client.commodity.rpc.SkuApi;
import com.ark.component.dto.MultiResponse;
import com.ark.component.dto.PageResponse;
import com.ark.component.dto.ServerResponse;
import com.ark.component.dto.SingleResponse;
import com.ark.component.validator.ValidateGroup;
import com.ark.component.web.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * SKU
 * </p>
 *
 * @author EOP
 * @since 2022-03-03
 */
@Api(tags = "SKU接口")
@Validated
@RestController
@RequestMapping("/v1/sku")
@RequiredArgsConstructor
@Slf4j
public class SkuController implements SkuApi {

    private final SkuAppService skuAppService;

    @Override
    public MultiResponse<SkuDTO> getSkuInfoList(SkuQry qry) {
        return MultiResponse.ok(skuAppService.querySkuList(qry));
    }
}
