package com.ark.center.commodity.app.commodity.executor;

import com.alibaba.fastjson2.JSONObject;
import com.ark.center.commodity.client.commodity.command.AttrCmd;
import com.ark.center.commodity.client.commodity.command.AttrOptionCmd;
import com.ark.center.commodity.client.commodity.command.CommodityCreateCmd;
import com.ark.center.commodity.client.commodity.command.SkuCmd;
import com.ark.center.commodity.domain.attachment.Attachment;
import com.ark.center.commodity.domain.attachment.gateway.AttachmentGateway;
import com.ark.center.commodity.domain.attr.AttrOption;
import com.ark.center.commodity.domain.spu.*;
import com.ark.center.commodity.domain.spu.assembler.SkuAssembler;
import com.ark.center.commodity.domain.spu.gateway.SkuGateway;
import com.ark.center.commodity.domain.spu.gateway.SpuGateway;
import com.ark.center.commodity.infra.commodity.AttachmentBizType;
import com.ark.center.commodity.infra.commodity.convertor.SpuConverter;
import com.ark.center.commodity.infra.commodity.gateway.cache.CommodityCacheConst;
import com.ark.component.cache.CacheService;
import com.ark.component.orm.mybatis.base.BaseEntity;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CommodityCreateCmdExe {

    private final SpuGateway spuGateway;
    private final SkuGateway skuGateway;
    private final AttachmentGateway attachmentGateway;
    private final SpuConverter spuConverter;
    private final SkuAssembler skuAssembler;
    private final CacheService cacheService;

    public Long execute(CommodityCreateCmd cmd) {
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
        saveAttrOptions(cmd, spu);
        // 保存Sku
        saveSku(cmd, spu);
        return spu.getId();
    }

    @NotNull
    private Spu toSpu(CommodityCreateCmd cmd) {
        Spu spu = spuConverter.toSpu(cmd);
        // 暂时取第一个sku的销售价格来做展示
        spu.setShowPrice(cmd.getSkuList().get(0).getSalesPrice());
        spu.setMainPicture(cmd.getPicList().get(0));
        return spu;
    }

    private void saveSku(CommodityCreateCmd cmd, Spu spu) {
        Long spuId = spu.getId();
        if (cmd.getId() != null && cmd.getFlushSku()) {
            // todo 把当前的SKU记录到历史表里面
            removeSkuAttrs(spuId);
            removeSku(spuId);
        }
        List<SkuCmd> skuList = cmd.getSkuList();

        Map<String, Object> stockMap = new HashMap<>(skuList.size());
        for (SkuCmd skuCmd : skuList) {
            Sku sku = skuAssembler.toSku(spu, skuCmd, cmd.getPicList().get(0));
            if (sku.getId() != null && !cmd.getFlushSku()) {
                skuGateway.update(sku);
            } else {
                skuGateway.insert(sku);
            }
            saveSkuAttrs(sku.getId(), skuCmd.getSpecList());

            stockMap.put(CommodityCacheConst.REDIS_SKU_STOCK + skuCmd.getId(), skuCmd.getStock());
        }
        // 批量缓存SKU库存
        cacheService.multiSet(stockMap);
    }

    private void saveSkuAttrs(Long skuId, List<AttrCmd> specList) {
        List<SkuAttr> doList = specList.stream().map(item -> {
            SkuAttr skuAttr = new SkuAttr();
            skuAttr.setSkuId(skuId);
            skuAttr.setAttrValue(item.getAttrValue());
            return skuAttr;
        }).toList();
        skuGateway.saveAttrs(doList);
    }

    private void removeSku(Long spuId) {
        skuGateway.deleteBySpuId(spuId);
    }


    private void removeSkuAttrs(Long spuId) {
        skuGateway.deleteAttrsBySpuId(spuId);
    }

    private void saveAttrOptions(CommodityCreateCmd cmd, Spu spu) {
        List<AttrOptionCmd> attrOptionList = cmd.getNewAttrOptionList();
        if (CollectionUtils.isEmpty(attrOptionList)) {
            return;
        }
        attrOptionList.forEach(attrOptionReqDTO -> batchSaveAttrOption(spu, attrOptionReqDTO));
    }

    private void batchSaveAttrOption(Spu spu, AttrOptionCmd option) {
        Long attrId = option.getAttrId();
        List<String> values = option.getValueList();
        Long spuId = spu.getId();
        List<AttrOption> dos = new ArrayList<>(values.size());
        for (String value : values) {
            AttrOption valueDO = new AttrOption();
            valueDO.setAttrId(attrId);
            valueDO.setValue(value);
            valueDO.setType(AttrOption.Type.EXCLUSIVE.getValue());
            valueDO.setSpuId(spuId);
            dos.add(valueDO);
        }
        spuGateway.saveAttrOptions(dos);
    }

    private void saveParams(CommodityCreateCmd cmd, Spu spu) {
        Long spuId = spu.getId();
        List<SpuAttr> records = spuGateway.selectAttrsBySpuId(spuId);
        // 如果spu原本存在数据，先删除
        if (CollectionUtils.isNotEmpty(records)) {
            spuGateway.batchDeleteAttrs(records);
        }
        List<SpuAttr> attrs = cmd.getParamList()
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

    private void savePictures(CommodityCreateCmd cmd, Spu spu) {
        List<Attachment> attachments = attachmentGateway.selectByBizTypeAndBizId(AttachmentBizType.SPU_PIC, spu.getId());
        if (CollectionUtils.isNotEmpty(attachments)) {
            List<Long> ids = attachments.stream().map(BaseEntity::getId).toList();
            attachmentGateway.deleteByIds(ids);
        }
        List<String> picList = cmd.getPicList();
        if (CollectionUtils.isNotEmpty(picList)) {
            picList.stream()
                    .map(picUrl -> {
                        Attachment attachment = new Attachment();
                        attachment.setBizType(AttachmentBizType.SPU_PIC);
                        attachment.setBizId(spu.getId());
                        attachment.setUrl(picUrl);
                        return attachment;
                    })
                    .toList()
                    .forEach(attachmentGateway::insert);
        }
    }

    private void saveSalesInfo(Spu spu, CommodityCreateCmd cmd) {
        SpuSales sales = new SpuSales();
        sales.setSpuId(spu.getId());
        sales.setFreightTemplateId(cmd.getFreightTemplateId());
        sales.setPcDetailHtml(cmd.getPcDetailHtml());
        sales.setMobileDetailHtml(cmd.getMobileDetailHtml());
        sales.setParamData(JSONObject.toJSONString(cmd.getParamList()));
        spuGateway.saveSales(sales);
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
