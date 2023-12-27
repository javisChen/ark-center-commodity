package com.ark.center.product.app.goods.executor;

import com.alibaba.fastjson2.JSONObject;
import com.ark.center.product.client.goods.command.AttrOptionCmd;
import com.ark.center.product.client.goods.command.GoodsCmd;
import com.ark.center.product.domain.attachment.Attachment;
import com.ark.center.product.domain.attachment.gateway.AttachmentGateway;
import com.ark.center.product.domain.attr.AttrOption;
import com.ark.center.product.domain.spu.Spu;
import com.ark.center.product.domain.spu.SpuAttr;
import com.ark.center.product.domain.spu.SpuSales;
import com.ark.center.product.domain.spu.gateway.SpuGateway;
import com.ark.center.product.domain.sku.SkuService;
import com.ark.center.product.infra.product.AttachmentBizType;
import com.ark.center.product.infra.product.convertor.SpuConverter;
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
    private final SpuGateway spuGateway;
    private final AttachmentGateway attachmentGateway;
    private final SpuConverter spuConverter;

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
        Spu spu = spuConverter.toSpu(cmd);
        // 暂时取第一个sku的销售价格来做展示
        spu.setShowPrice(cmd.getSkus().get(0).getSalesPrice());
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
            spuGateway.saveAttrOptions(options);
        }
    }

    private void saveParams(GoodsCmd cmd, Spu spu) {
        Long spuId = spu.getId();
        List<SpuAttr> records = spuGateway.selectAttrsBySpuId(spuId);

        // 先尝试清除原本的SPU参数属性
        attemptClearSpuParams(records);

        List<SpuAttr> attrs = cmd.getParams()
                .stream()
                .map(item -> {
                    SpuAttr spuAttr = new SpuAttr();
                    spuAttr.setSpuId(spuId);
                    spuAttr.setAttrId(item.getAttrId());
                    spuAttr.setAttrValue(item.getAttrValue());
                    return spuAttr;
                })
                .toList();
        spuGateway.insertAttrs(attrs);
    }

    private void attemptClearSpuParams(List<SpuAttr> records) {
        // 如果spu原本存在数据，先删除
        if (CollectionUtils.isNotEmpty(records)) {
            List<Long> ids = records.stream().map(SpuAttr::getAttrId).toList();
            spuGateway.batchDeleteAttrs(ids);
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
        sales.setParamData(JSONObject.toJSONString(cmd.getParams()));
        boolean updated = spuGateway.updateSpuSales(sales);
        if (!updated) {
            spuGateway.saveSpuSales(sales);
        }
    }

    private Long saveBaseInfo(Spu spu) {
        if (spu.getId() != null) {
            spuGateway.updateSpu(spu);
        } else {
            spuGateway.saveSpu(spu);
        }
        return spu.getId();
    }

}
