package com.ark.center.product.app.goods.executor;

import com.ark.center.product.client.goods.command.AttrOptionCmd;
import com.ark.center.product.client.goods.command.GoodsCmd;
import com.ark.center.product.infra.attachment.Attachment;
import com.ark.center.product.infra.attachment.gateway.AttachmentGateway;
import com.ark.center.product.infra.attr.AttrOption;
import com.ark.center.product.infra.product.AttachmentBizType;
import com.ark.center.product.infra.sku.SkuService;
import com.ark.center.product.infra.spu.Spu;
import com.ark.center.product.infra.spu.SpuAttr;
import com.ark.center.product.infra.spu.SpuSales;
import com.ark.center.product.infra.spu.assembler.SpuAssembler;
import com.ark.center.product.infra.product.service.SpuService;
import com.ark.component.orm.mybatis.base.BaseEntity;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GoodsCmdExe {

    private final SkuService skuService;
    private final SpuService spuService;
    private final AttachmentGateway attachmentGateway;
    private final SpuAssembler spuAssembler;

    public Long execute(GoodsCmd cmd) {
        Spu spu = toSpu(cmd);
        // 保存SPU基本信息
        saveBaseInfo(spu);
        // 保存销售信息
        saveSalesInfo(spu, cmd);
        // 保存图片
        savePictures(cmd, spu);
        // 保存参数属性
        saveParams(cmd, spu);
        // 保存属性可选项
        saveExclusiveAttrOptions(cmd, spu);
        // 保存Sku
        saveSku(cmd, spu);

        return spu.getId();
    }

    @NotNull
    private Spu toSpu(GoodsCmd cmd) {
        Spu spu = spuAssembler.toSpu(cmd);
        // 暂时取第一个sku的销售价格来做展示
//        spu.setSalesPrice(cmd.getSkus().get(0).getSalesPrice());
        spu.setMainPicture(cmd.getPictures().get(0));
        return spu;
    }

    private void saveSku(GoodsCmd cmd, Spu spu) {
        skuService.saveSku(cmd, spu);
    }

    private void saveExclusiveAttrOptions(GoodsCmd cmd, Spu spu) {
        List<AttrOptionCmd> attrOptionList = cmd.getNewAttrOptionList();
        if (CollectionUtils.isEmpty(attrOptionList)) {
            return;
        }
        Long spuId = spu.getId();
        for (AttrOptionCmd optionCmd : attrOptionList) {
            Long attrId = optionCmd.getAttrId();
            List<String> values = optionCmd.getValueList();
            List<AttrOption> options = values.stream().map(value -> {
                AttrOption option = new AttrOption();
                option.setSpuId(spuId);
                option.setAttrId(attrId);
                option.setValue(value);
                option.setType(AttrOption.Type.EXCLUSIVE.getValue());
                return option;
            }).toList();
            spuService.saveAttrOptions(options);
        }
    }

    private void saveParams(GoodsCmd cmd, Spu spu) {
        Long spuId = spu.getId();

        // 先尝试清除原本的SPU参数属性
        attemptClearSpuParams(spuId);

        List<SpuAttr> attrs = cmd.getParams()
                .stream()
                .map(item -> {
                    SpuAttr spuAttr = new SpuAttr();
                    spuAttr.setSpuId(spuId);
                    spuAttr.setAttrId(item.getAttrId());
                    spuAttr.setAttrName(item.getAttrName());
                    spuAttr.setAttrValue(item.getAttrValue());
                    return spuAttr;
                })
                .toList();
        spuService.insertAttrs(attrs);
    }

    private void attemptClearSpuParams(Long spuId) {
        List<SpuAttr> params = spuService.selectAttrsBySpuId(spuId);
        // 如果spu原本存在数据，先删除
        if (CollectionUtils.isNotEmpty(params)) {
            List<Long> ids = params.stream().map(SpuAttr::getAttrId).sorted().toList();
            spuService.batchDeleteAttrs(ids);
        }
    }

    private void savePictures(GoodsCmd cmd, Spu spu) {

        Long spuId = spu.getId();

        List<Attachment> attachments = attachmentGateway.selectByBizTypeAndBizId(AttachmentBizType.SPU_PIC, spuId);

        // 尝试先清除原有的图片
        attemptClearPictures(attachments);

        List<String> pictures = cmd.getPictures();
        if (CollectionUtils.isNotEmpty(pictures)) {
            List<Attachment> insertList = pictures.stream()
                    .map(picUrl -> {
                        Attachment attachment = new Attachment();
                        attachment.setBizType(AttachmentBizType.SPU_PIC);
                        attachment.setBizId(spuId);
                        attachment.setUrl(picUrl);
                        return attachment;
                    })
                    .toList();
            attachmentGateway.batchInsert(insertList);
        }
    }

    private void attemptClearPictures(List<Attachment> attachments) {
        if (CollectionUtils.isNotEmpty(attachments)) {
            List<Long> ids = attachments.stream().map(BaseEntity::getId).toList();
            attachmentGateway.deleteByIds(ids);
        }
    }

    private void saveSalesInfo(Spu spu, GoodsCmd cmd) {
        SpuSales sales = new SpuSales();
        sales.setSpuId(spu.getId());
        sales.setFreightTemplateId(cmd.getFreightTemplateId());
        sales.setPcRichText(cmd.getPcRichText());
        sales.setMobileRichText(cmd.getMobileRichText());
        sales.setParamData(spuAssembler.toSpuAttr(cmd.getParams()));
        boolean updated = spuService.updateSpuSales(sales);
        if (!updated) {
            spuService.saveSpuSales(sales);
        }
    }

    private Long saveBaseInfo(Spu spu) {
        if (spu.getId() != null) {
            spuService.updateSpu(spu);
        } else {
            spuService.saveSpu(spu);
        }
        return spu.getId();
    }

}
