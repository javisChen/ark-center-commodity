package com.ark.center.product.infra.inventory.service;

import cn.hutool.core.collection.CollUtil;
import com.ark.center.product.client.inventory.command.StockLockCmd;
import com.ark.center.product.client.inventory.dto.StockLockDTO;
import com.ark.center.product.infra.inventory.Inventory;
import com.ark.center.product.infra.inventory.InventoryRecord;
import com.ark.center.product.infra.inventory.cache.InventoryCache;
import com.ark.center.product.infra.inventory.repository.db.InventoryMapper;
import com.ark.center.product.infra.inventory.repository.db.InventoryRecordMapper;
import com.ark.component.exception.ExceptionFactory;
import com.ark.component.orm.mybatis.base.BaseEntity;
import com.ark.component.web.util.separate.DataSeparator;
import com.ark.component.web.util.separate.SeparateResult;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品库存领域服务
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService extends ServiceImpl<InventoryMapper, Inventory> {

    private final InventoryCache inventoryCacheGateway;

    private final InventoryRecordMapper inventoryRecordMapper;

    public List<Inventory> selectBySpuId(Long spuId) {
        return lambdaQuery()
                .select(Inventory::getSpuId,
                        Inventory::getSkuId,
                        Inventory::getAvailableStock,
                        Inventory::getSoldStock,
                        Inventory::getLockedStock
                )
                .eq(Inventory::getSpuId, spuId)
                .list();
    }

    public void delete(List<Long> ids) {
        ids = ids.stream().sorted().toList();
        lambdaUpdate()
                .in(BaseEntity::getId, ids)
                .remove();
    }

    public void deleteRecords(List<Long> ids) {
        ids = ids.stream().sorted().toList();
        LambdaUpdateWrapper<InventoryRecord> uw = Wrappers.lambdaUpdate(InventoryRecord.class)
                .in(InventoryRecord::getInventoryId, ids);
        inventoryRecordMapper.delete(uw);
    }

    public List<Inventory> selectBySkuIds(List<Long> skuIds) {
        return lambdaQuery()
                .select(BaseEntity::getId,
                        Inventory::getSpuId,
                        Inventory::getSkuId,
                        Inventory::getAvailableStock,
                        Inventory::getSoldStock,
                        Inventory::getLockedStock
                )
                .in(Inventory::getSkuId, skuIds)
                .list();
    }

    public void updateAvailableStock(Inventory inventory) {
        lambdaUpdate()
                .eq(Inventory::getSkuId, inventory.getSkuId())
                .set(Inventory::getAvailableStock, inventory);
    }

    /**
     * 保存库存
     * 根据SkuId
     */
    public void save(List<Inventory> inventories) {
        List<Long> skuIds = CollUtil.map(inventories, Inventory::getSkuId, true);
        List<Inventory> originalInventories = selectBySkuIds(skuIds);
        //
        if (CollectionUtils.isEmpty(originalInventories)) {
            executeSave(inventories);
            return;
        }

        // 区分出新增/更新的数据
        SeparateResult<Inventory> result = DataSeparator.separate(inventories, originalInventories, Inventory::getSkuId);
        List<Inventory> inserts = result.getInserts();
        List<Inventory> updates = result.getUpdates();

        // todo 下面的存储存在缓存与DB的一致性问题，后续优化
        if (CollectionUtils.isNotEmpty(inserts)) {
            executeSave(inserts);
        }

        if (CollectionUtils.isNotEmpty(updates)) {
            executeUpdateAvailable(updates);
        }

    }

    private void executeUpdateAvailable(List<Inventory> updateInventories) {
        for (Inventory inventory : updateInventories) {
            updateAvailableStock(inventory);
        }
        inventoryCacheGateway.updateAvailableStock(updateInventories);
    }

    private void executeSave(List<Inventory> inventories) {
        save(inventories);
        inventoryCacheGateway.save(inventories);
    }

    /**
     * 锁定库存
     * 1.先从缓存中尝试扣减可用库存
     * 2.创建异步任务
     *
     */
    public StockLockDTO lockStock(StockLockCmd cmd) {
        StockLockDTO result = new StockLockDTO();

        List<StockLockCmd.Item> willLockItems = cmd.getItems();

        List<StockLockCmd.Item> decrSuccessfulSkuIds = new ArrayList<>(willLockItems.size());

        StockLockCmd.Item decrFailureSkuId = doLockStock(willLockItems, decrSuccessfulSkuIds);

        if (decrFailureSkuId != null) {
            // 返还库存
            returnStock(decrSuccessfulSkuIds);
            throw ExceptionFactory.userException(decrFailureSkuId.getSkuId() + "库存不足");
        }
        return result;
    }

    /**
     * 返回已扣减的库存
     * todo 这里需要优化，把每一个需要返还库存的sku都写到返还表中
     *
     * @param decrSuccessfulSkuIds 已锁定成功的库存
     */
    private void returnStock(List<StockLockCmd.Item> decrSuccessfulSkuIds) {
        for (StockLockCmd.Item item : decrSuccessfulSkuIds) {
            Long quantity = Long.valueOf(item.getQuantity());
            Long skuId = item.getSkuId();
            inventoryCacheGateway.incrAvailableStock(skuId, quantity);
        }
    }

    @Nullable
    private StockLockCmd.Item doLockStock(List<StockLockCmd.Item> willLockItems, List<StockLockCmd.Item> decrSuccessfulSkuIds) {
        StockLockCmd.Item decrFailureSkuId = null;

        for (StockLockCmd.Item item : willLockItems) {
            Long quantity = Long.valueOf(item.getQuantity());
            Long skuId = item.getSkuId();
            // 扣减失败有两种情况
            // 1.库存不足
            // 2.请求redis异常
            // 只要失败就把当前处理的库存标记一下
            try {
                Long remain = inventoryCacheGateway.decrAvailableStock(skuId, quantity);
                if (remain > 0) {
                    decrSuccessfulSkuIds.add(item);
                } else {
                    decrFailureSkuId = item;
                }
            } catch (Exception e) {
                decrFailureSkuId = item;
            }
        }
        return decrFailureSkuId;
    }

    public void clearStock(List<Long> skuIds) {

        List<Inventory> inventories = selectBySkuIds(skuIds);
        if (CollectionUtils.isEmpty(inventories)) {
            return;
        }

        List<Long> ids = inventories.stream().map(BaseEntity::getId).toList();
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }

        // 先删除库存缓存
        inventoryCacheGateway.delStock(inventories);

        //  删除库存
        delete(ids);

        // 删除库存操作记录
        deleteRecords(ids);

    }
}
