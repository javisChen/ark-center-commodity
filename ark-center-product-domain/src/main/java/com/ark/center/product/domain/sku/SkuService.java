package com.ark.center.product.domain.sku;

import com.ark.center.product.client.goods.command.AttrCmd;
import com.ark.center.product.client.goods.command.GoodsCmd;
import com.ark.center.product.client.goods.command.SkuCmd;
import com.ark.center.product.client.goods.dto.GoodsAttrDTO;
import com.ark.center.product.client.goods.dto.SkuDTO;
import com.ark.center.product.domain.inventory.Inventory;
import com.ark.center.product.domain.inventory.service.InventoryService;
import com.ark.center.product.domain.sku.assembler.SkuAssembler;
import com.ark.center.product.domain.sku.gateway.SkuGateway;
import com.ark.center.product.domain.spu.Spu;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.SetUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SkuService {

    private final SkuAssembler skuAssembler;
    private final SkuGateway skuGateway;
    private final InventoryService inventoryService;

    public void saveSku(GoodsCmd cmd, Spu spu) {

        boolean skuCombinationHasChanged = checkSkuCombination(cmd);

        List<Sku> skus = saveSkus(cmd, spu);

        // 保存库存
        saveInventory(spu.getId(), skus);
    }

    private List<Sku> saveSkus(GoodsCmd cmd, Spu spu) {

        List<Sku> skus = Lists.newArrayList();

        for (SkuCmd skuCmd : cmd.getSkus()) {

            Sku sku = skuAssembler.toSku(spu, skuCmd, cmd.getPictures().get(0));

            saveSku(sku);

            saveSkuAttrs(sku);

            skus.add(sku);
        }
        return skus;
    }

    private void saveInventory(Long spuId, List<Sku> skus) {
        List<Inventory> inventories = skus.stream().map(sku -> {
            Inventory inventory = new Inventory();
            inventory.setSpuId(spuId);
            inventory.setSkuId(sku.getId());
            inventory.setAvailableStock(sku.getStock());
            return inventory;
        }).toList();

        inventoryService.save(inventories);
    }

    /**
     * 判断SKU组合是否发生改变
     */
    private boolean checkSkuCombination(GoodsCmd cmd) {
        if (cmd.getId() == null) {
            return false;
        }

        List<SkuDTO> originalSkus = skuGateway.selectBySpuId(cmd.getId());

        if (skuIsEqual(cmd.getSkus(), originalSkus)) {
            return false;
        }

        // todo 把当前的SKU记录到历史表里面

        List<Long> skuIds = originalSkus.stream().map(SkuDTO::getId).toList();

        // 清除SKU属性值
        skuGateway.deleteAttrsBySkuIds(skuIds);

        // 清除SKU
        skuGateway.deleteBySkuIds(skuIds);

        // 清除库存
        inventoryService.clearStock(skuIds);

        return true;
    }

    /**
     * 比对新旧SKU是否相等
     *
     * @param skus         请求的SKU
     * @param originalSkus 原有的SKU
     */
    private boolean skuIsEqual(List<SkuCmd> skus, List<SkuDTO> originalSkus) {
        List<AttrCmd> specs = skus.get(0).getSpecs();
        List<GoodsAttrDTO> originalSkuSpecs = originalSkus.get(0).getSpecs();
        if (specs.size() != originalSkuSpecs.size()) {
            return false;
        }
        Set<Long> currSet = specs.stream().map(AttrCmd::getAttrId).collect(Collectors.toSet());
        Set<Long> oldSet = originalSkuSpecs.stream().map(GoodsAttrDTO::getAttrId).collect(Collectors.toSet());
        boolean equalSet = SetUtils.isEqualSet(currSet, oldSet);
        if (!equalSet) {
            return false;
        }
        Set<Long> skuIds = skus.stream().map(SkuCmd::getId).collect(Collectors.toSet());
        Set<Long> originalSkuIds = originalSkus.stream().map(SkuDTO::getId).collect(Collectors.toSet());
        return SetUtils.isEqualSet(skuIds, originalSkuIds);
    }

    private void saveSku(Sku sku) {
        if (sku.getId() != null) {
            skuGateway.update(sku);
        } else {
            skuGateway.insert(sku);
        }
    }

    private void saveSkuAttrs(Sku sku) {
        List<SkuAttr> specs = sku.getSpecs();
        Long skuId = sku.getId();
        List<SkuAttr> attrs = specs.stream().map(item -> {
            SkuAttr skuAttr = new SkuAttr();
            skuAttr.setSkuId(skuId);
            skuAttr.setAttrId(item.getAttrId());
            skuAttr.setAttrName(item.getAttrName());
            skuAttr.setAttrValue(item.getAttrValue());
            return skuAttr;
        }).toList();
        skuGateway.saveAttrs(attrs);
    }

}
