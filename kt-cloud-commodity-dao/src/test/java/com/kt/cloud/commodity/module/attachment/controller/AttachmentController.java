package com.kt.cloud.commodity.module.attachment.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kt.cloud.commodity.module.attachment.dto.request.AttachmentCreateReqDTO;
import com.kt.cloud.commodity.module.attachment.dto.request.AttachmentUpdateReqDTO;
import com.kt.cloud.commodity.module.attachment.dto.request.AttachmentPageQueryReqDTO;
import com.kt.cloud.commodity.module.attachment.dto.response.AttachmentRespDTO;
import com.kt.cloud.commodity.module.attachment.dto.response.AttachmentRespDTO;
import com.kt.cloud.commodity.module.attachment.service.AttachmentService;
import com.kt.component.dto.PageResponse;
import com.kt.component.dto.SingleResponse;
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
 * 附件表 前端控制器
 * </p>
 *
 * @author EOP
 * @since 2022-04-22
 */
@Api(tags = "附件表")
@Validated
@RestController
@RequestMapping("/v1/attachment")
public class AttachmentController extends BaseController {

    private final AttachmentService attachmentService;

    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @ApiOperation(value = "创建附件表")
    @PostMapping("/create")
    public SingleResponse<Long> create(@RequestBody @Validated AttachmentUpdateReqDTO reqDTO) {
        return SingleResponse.ok(attachmentService.createAttachment(reqDTO));
    }

    @ApiOperation(value = "修改附件表")
    @PostMapping("/update")
    public SingleResponse<Long> update(@RequestBody @Validated(ValidateGroup.Update.class) AttachmentUpdateReqDTO reqDTO) {
        return SingleResponse.ok(attachmentService.updateAttachment(reqDTO));
    }

    @ApiOperation(value = "查询附件表分页列表")
    @PostMapping("/page")
    public SingleResponse<PageResponse<AttachmentRespDTO>> pageList(@RequestBody @Validated AttachmentPageQueryReqDTO queryDTO) {
        return SingleResponse.ok(attachmentService.getPageList(queryDTO));
    }

    @ApiOperation(value = "查询附件表详情")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @GetMapping("/info")
    public SingleResponse<AttachmentRespDTO> info(@RequestParam(required = false) @NotNull(message = "id不能为空") Long id) {
        return SingleResponse.ok(attachmentService.getAttachmentInfo(id));
    }


}
