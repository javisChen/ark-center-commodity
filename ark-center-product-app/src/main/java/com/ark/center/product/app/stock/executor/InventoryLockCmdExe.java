package com.ark.center.product.app.stock.executor;

import cn.hutool.core.io.FileUtil;
import com.ark.center.product.client.inventory.command.StockLockCmd;
import com.ark.center.product.client.inventory.dto.StockLockDTO;
import com.ark.center.product.domain.inventory.service.InventoryService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.List;
import java.util.StringJoiner;

@Data
@RequiredArgsConstructor
@Component
@Slf4j
public class InventoryLockCmdExe {

    private final InventoryService inventoryService;

    /**
     * todo 需要优化，目前是比较简单的实现
     */
    public StockLockDTO execute(StockLockCmd cmd) {
        log.info("执行库存扣减 = {}", cmd.getItems());
        StockLockDTO decreaseDTO = new StockLockDTO();
//        for (StockLockCmd.Item item : cmd.getItems()) {
//            Long quantity = Long.valueOf(item.getQuantity());
//            Long skuId = item.getSkuId();
//            Long remain = cacheService.decrement(InventoryCacheKeys.SKU_STOCK + skuId, quantity);
//            if (remain < 0) {
//                cacheService.increment(InventoryCacheKeys.SKU_STOCK + skuId, quantity);
//                decreaseDTO.setResult(false);
//                decreaseDTO.setSkuId(Lists.newArrayList(skuId));
//                // throw ExceptionFactory.userException("商品库存不足！");
//            }
//        }
//        log.info("执行库存扣减成功...");
        return decreaseDTO;
    }

    public static void main(String[] args) {
        buildInsertSqls();
//        check();
//        buildQuerySqls();
    }

    private static void check() {
        List<String> exists = FileUtil.readLines(new File("/Users/chenjiawei/code/myself/ark/ark-center-product/ark-center-product-infra/src/main/java/com/ark/center/product/infra/inventory/gateway/cache/exists.txt"), Charset.defaultCharset());
        HashSet<String> hashSet = new HashSet<>(exists);

        List<String> codes = FileUtil.readLines(new File("/Users/chenjiawei/code/myself/ark/ark-center-product/ark-center-product-infra/src/main/java/com/ark/center/product/infra/inventory/gateway/cache/code.sql"), Charset.defaultCharset());
        for (int i = 0; i < codes.size(); i++) {
            String code = codes.get(i);
            if (!hashSet.contains(code)) {
                System.out.println(code + " -> " + (i + 1));
            }
        }

    }

    private static void buildQuerySqls() {
        String s = "delete from us_button where code in (";
        File dest = new File("/Users/chenjiawei/code/myself/ark/ark-center-product/ark-center-product-infra/src/main/java/com/ark/center/product/infra/inventory/gateway/cache/query.sql");
        FileUtil.del(dest);

        List<String> codes = FileUtil.readLines(new File("/Users/chenjiawei/code/myself/ark/ark-center-product/ark-center-product-infra/src/main/java/com/ark/center/product/infra/inventory/gateway/cache/code.sql"), Charset.defaultCharset());
        StringJoiner stringJoiner = new StringJoiner(",");
        for (String code : codes) {
            stringJoiner.add("'" + code + "'");
        }
        s = s + stringJoiner + ")";
        FileUtil.writeUtf8String(s, dest);
    }

    private static void buildInsertSqls() {
        File dest = new File("/Users/chenjiawei/code/myself/ark/ark-center-product/ark-center-product-infra/src/main/java/com/ark/center/product/infra/inventory/gateway/cache/new.sql");
        FileUtil.del(dest);

        dest = new File("/Users/chenjiawei/code/myself/ark/ark-center-product/ark-center-product-infra/src/main/java/com/ark/center/product/infra/inventory/gateway/cache/afterNamed.sql");
        FileUtil.del(dest);

        List<String> names = FileUtil.readLines(new File("/Users/chenjiawei/code/myself/ark/ark-center-product/ark-center-product-infra/src/main/java/com/ark/center/product/infra/inventory/gateway/cache/name.sql"), Charset.defaultCharset());
        List<String> codes = FileUtil.readLines(new File("/Users/chenjiawei/code/myself/ark/ark-center-product/ark-center-product-infra/src/main/java/com/ark/center/product/infra/inventory/gateway/cache/code.sql"), Charset.defaultCharset());
        String replace = "INSERT INTO `redex_center_user`.`us_button` (`id`, `code`, `name`, `status`, `menu_id`, `parent_code`, `create_person`, `create_time`, `update_person`, `update_time`, `description`)" +
                "VALUES ('${id}', '${code}', '${name}', '1', '1355693979587586801', 'YWCSPZ', 'yxluoqh', '2023-10-27 17:49:42', 'yxluoqh', '2023-10-27 17:49:42', '');\n";
        long startId = 1595248838528020685L;
        for (int i = 0; i < names.size(); i++) {
            startId += 1;
            String replaced = replace.replace("'${name}'", "'" + names.get(i) + "'");
            replaced = replaced.replace("'${code}'", "'" + codes.get(i) + "'");
            replaced = replaced.replace("'${id}'", String.valueOf(startId));
            FileUtil.appendString(replaced, dest, Charset.defaultCharset());
        }
    }
}
