package com.ark.center.product.infra.sku;

import com.ark.center.product.client.goods.command.AttrCmd;
import com.ark.center.product.client.goods.command.GoodsCmd;
import com.ark.center.product.client.goods.command.SkuCmd;
import com.ark.center.product.client.goods.dto.GoodsAttrDTO;
import com.ark.center.product.client.goods.dto.SkuDTO;
import com.ark.center.product.infra.attr.gateway.AttrGateway;
import com.ark.center.product.infra.inventory.Inventory;
import com.ark.center.product.infra.inventory.service.InventoryService;
import com.ark.center.product.infra.product.gateway.db.SkuAttrMapper;
import com.ark.center.product.infra.product.gateway.db.SkuMapper;
import com.ark.center.product.infra.sku.assembler.SkuAssembler;
import com.ark.center.product.infra.spu.Spu;
import com.ark.component.orm.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.SetUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SkuService extends ServiceImpl<SkuMapper, Sku> {

    private final SkuAssembler skuAssembler;
    private final InventoryService inventoryService;
    private final SkuAttrMapper skuAttrMapper;
    private final AttrGateway attrGateway;

    public List<Sku> queryByIds(List<Long> ids) {
        return listByIds(ids);
    }

    public List<SkuDTO> selectBySpuId(Long spuId) {
        List<Sku> skuList = lambdaQuery()
                .eq(Sku::getSpuId, spuId)
                .list();
        return skuAssembler.toSkuDTO(skuList);
    }

    public List<SkuDTO> selectBySpuIds(List<Long> spuIds) {
        List<Sku> skuList = lambdaQuery()
                .in(Sku::getSpuId, spuIds)
                .list();
        List<SkuDTO> skuDTO = skuAssembler.toSkuDTO(skuList);
        for (SkuDTO dto : skuDTO) {
            List<GoodsAttrDTO> specs = dto.getSpecs();
        }
        return skuDTO;
    }

    public void insert(Sku sku) {
        save(sku);
    }

    public void saveAttrs(List<SkuAttr> attrs) {

    }

    public void deleteBySpuId(Long spuId) {
        List<Sku> records = lambdaQuery()
                .select(BaseEntity::getId)
                .eq(Sku::getSpuId, spuId)
                .list();
        if (CollectionUtils.isNotEmpty(records)) {
            removeByIds(records);
        }
    }

    public void deleteAttrsBySpuId(Long spuId, List<SkuDTO> originalSkus) {
        List<Sku> skuList = lambdaQuery()
                .select(BaseEntity::getId)
                .eq(Sku::getSpuId, spuId)
                .list();
        if (CollectionUtils.isEmpty(skuList)) {
            return;
        }

        List<Long> skuIds = skuList.stream().map(BaseEntity::getId).sorted().collect(Collectors.toList());

        deleteAttrsBySkuIds(skuIds);
    }

    public void deleteAttrsBySkuIds(List<Long> skuIds) {
        LambdaQueryWrapper<SkuAttr> qw = Wrappers.lambdaQuery(SkuAttr.class)
                .select(BaseEntity::getId)
                .in(SkuAttr::getSkuId, skuIds);
        List<SkuAttr> skuAttrs = skuAttrMapper.selectList(qw);
        if (CollectionUtils.isEmpty(skuAttrs)) {
            return;
        }
        List<Long> ids = skuAttrs.stream().map(BaseEntity::getId).sorted().toList();
        skuAttrMapper.deleteBatchIds(ids);
    }

    public void update(Sku sku) {
        updateById(sku);
    }

    public void saveSku(GoodsCmd cmd, Spu spu) {

        List<SkuDTO> originalSkus = selectBySpuId(cmd.getId());

        if (skuIsChanged(cmd, originalSkus)) {
            // todo 把当前的SKU记录到历史表里面
            List<Long> skuIds = originalSkus.stream().map(SkuDTO::getId).toList();

            // 清除SKU属性值
            deleteAttrsBySkuIds(skuIds);

            // 清除SKU
            removeByIds(skuIds);

            // 清除库存
            inventoryService.clearStock(skuIds);
        }

        List<Sku> skus = saveSkus(cmd, spu);

        saveInventory(spu.getId(), skus);
    }

    private List<Sku> saveSkus(GoodsCmd cmd, Spu spu) {

        List<Sku> skus = Lists.newArrayList();

        for (SkuCmd skuCmd : cmd.getSkus()) {

            Sku sku = skuAssembler.toSku(spu, skuCmd, cmd.getPictures().getFirst());

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
    private boolean skuIsChanged(GoodsCmd cmd, List<SkuDTO> originalSkus) {
        if (cmd.getId() == null) {
            return false;
        }

        return !skuIsEqual(cmd.getSkus(), originalSkus);
    }

    /**
     * 比对新旧SKU是否相等
     *
     * @param skus         请求的SKU
     * @param originalSkus 原有的SKU
     */
    private boolean skuIsEqual(List<SkuCmd> skus, List<SkuDTO> originalSkus) {
        List<AttrCmd> specs = skus.getFirst().getSpecs();
        List<GoodsAttrDTO> originalSkuSpecs = originalSkus.getFirst().getSpecs();
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
        saveOrUpdate(sku);
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
        saveAttrs(attrs);
    }

}
