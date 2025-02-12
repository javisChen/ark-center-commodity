# ARK Center Product 工程规范

## 1. 模块划分

```
ark-center-product/
├── ark-center-product-adapter  // 适配器层，处理外部请求
│   └── src/main/java/com/ark/center/product/adapter/[module]
│       ├── web                // HTTP请求接口
│       │   ├── [Module]AdminController.java  // 管理后台接口
│       │   └── [Module]Controller.java       // 客户端接口
│       └── consumer           // MQ消费者
│           └── [Module]Consumer.java
├── ark-center-product-app     // 应用层，编排业务逻辑
│   └── src/main/java/com/ark/center/product/app/[module]
│       ├── [Module]CommandHandler.java  // 处理命令
│       └── [Module]QueryService.java    // 处理查询
├── ark-center-product-client  // API层，定义对外接口
│   └── src/main/java/com/ark/center/product/client
│       ├── common            // 通用定义
│       │   ├── [Module]Constant.java  // 常量定义
│       │   └── [Module]Enum.java      // 枚举定义
│       ├── [module]         // 业务模块
│       │   ├── [Module]CommandApi.java  // 命令API定义
│       │   ├── [Module]QueryApi.java    // 查询API定义
│       │   ├── command     // 命令对象
│       │   ├── dto        // 数据传输对象
│       │   └── query      // 查询对象
├── ark-center-product-domain  // 领域层（暂不采用）
├── ark-center-product-infra   // 基础设施层，实现细节
│   └── src/main/java/com/ark/center/product/infra/[module]
│       ├── gateway     // 远程调用防腐层
│       ├── service     // 模块逻辑封装
│       ├── repository  // 仓储层(DB+Cache)
│       └── config      // 配置类
└── ark-center-product-start   // 启动模块
    └── src/main/java/com/ark/center/product
        └── ProductApplication.java
```

## 2. 各层职责

### adapter层
- 位置：`adapter/{module}/web/`
- 职责：接收外部HTTP请求
- 命名规范：`{Module}Controller`
- 依赖：app层的CommandHandler和QueryService

### app层
- 位置：`app/{module}/`
- 职责：业务流程编排
- 包含组件：
  - `{Module}CommandHandler` - 处理命令
  - `{Module}QueryService` - 处理查询
  - `executor/` - 复杂命令的执行器
  - `assembler/` - DTO转换器

### client层
- 位置：`client/{module}/`
- 职责：定义对外API接口
- 包含：
  - `api/` - API接口定义
  - `common/` - 通用定义（常量、枚举等）
  - `[module]/` - 业务模块
    - `[Module]CommandApi.java` - 命令API定义
    - `[Module]QueryApi.java` - 查询API定义
    - `command/` - 命令对象
    - `query/` - 查询对象
    - `dto/` - 数据传输对象

### domain层
- 位置：`domain/{module}/`
- 职责：领域模型和核心业务规则
- 包含：
  - 领域对象
  - 领域服务
  - 领域事件

### infra层
- 位置：`infra/{module}/`
- 职责：基础设施实现
- 包含：
  - `gateway/` - 网关接口
  - `gateway/impl/` - 网关实现
  - `convertor/` - 对象转换器
  - `dao/` - 数据访问对象

## 3. 命名规范
- Controller层：`{Module}Controller`
- 命令处理：`{Module}CommandHandler`
- 查询服务：`{Module}QueryService`
- 命令对象：`{Module}{Action}Cmd`
- 查询对象：`{Module}PageQry`
- DTO对象：`{Module}DTO`
- 网关接口：`{Module}Gateway`
- 网关实现：`{Module}Service`

## 4. 依赖规则
- adapter层 → app层
- app层 → domain层
- app层 → infra层
- infra层 → domain层
- client层被其他层依赖

## 5. 设计模式
- CQRS模式：命令和查询分离
- Command Execute模式：复杂命令使用专门的执行器
- Gateway模式：通过网关接口访问外部资源
- DTO模式：数据传输对象在层间传递

## 6. 开发流程
1. 在client层定义接口、命令和查询对象
2. 在client层定义API接口（@FeignClient）
3. 在adapter层实现API接口
4. 在app层编排业务流程
5. 在infra层实现基础设施代码

## 7. 代码规范
- 所有的外部请求都必须通过adapter层进入系统
- 复杂的命令处理应该使用专门的执行器(Command Executor)
- 查询和命令必须分离
- 通过Gateway接口访问外部资源
- 使用DTO在层间传递数据
- 领域逻辑必须在domain层实现 

# 商品查询接口示例

## 1. API定义 (client层)

```java

package com.ark.center.product.client.product;

import com.ark.center.product.client.product.dto.ProductDTO;
import com.ark.center.product.client.product.query.ProductQuery;
import com.ark.component.dto.SingleResponse;
import com.ark.component.microservice.rpc.exception.FeignCommonErrorDecoder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
    name = "${ark.center.product.service.name:product}",
    path = "/v1/products",
    url = "${ark.center.product.service.uri:}",
    dismiss404 = true,
    configuration = FeignCommonErrorDecoder.class
)
@Tag(name = "商品查询接口")
public interface ProductQueryApi {

    @GetMapping("/detail")
    @Operation(summary = "查询商品详情")
    SingleResponse<ProductDTO> getProduct(@SpringQueryMap ProductQuery query);
}
```

```java

package com.ark.center.product.client.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "商品信息")
public class ProductDTO {
    @Schema(description = "商品ID")
    private Long id;
    
    @Schema(description = "商品名称")
    private String name;
    
    @Schema(description = "商品价格(分)")
    private Long price;
}
```

```java

package com.ark.center.product.client.product.query;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "商品查询参数")
public class ProductQuery {
    @NotNull(message = "商品ID不能为空")
    @Schema(description = "商品ID")
    private Long id;
}
```

## 2. Controller实现 (adapter层)

```java

package com.ark.center.product.adapter.product.web;

import com.ark.center.product.app.product.ProductQueryService;
import com.ark.center.product.client.product.ProductQueryApi;
import com.ark.center.product.client.product.dto.ProductDTO;
import com.ark.center.product.client.product.query.ProductQuery;
import com.ark.component.dto.SingleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController implements ProductQueryApi {
    
    private final ProductQueryService productQueryService;
    
    @Override
    public SingleResponse<ProductDTO> getProduct(ProductQuery query) {
        return SingleResponse.ok(productQueryService.getProduct(query));
    }
}
```

## 3. 流程编排 (app层)

```java

package com.ark.center.product.app.product;

import com.ark.center.product.client.product.dto.ProductDTO;
import com.ark.center.product.client.product.query.ProductQuery;
import com.ark.center.product.infra.product.service.ProductService;
import com.ark.center.product.infra.inventory.service.InventoryService;
import com.ark.center.product.infra.price.service.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductQueryService {
    
    private final ProductService productService;
    private final InventoryService inventoryService;
    private final PriceService priceService;
    
    public ProductDTO getProduct(ProductQuery query) {
        // 1. 获取商品基本信息
        ProductDTO product = productService.getProductBase(query.getId());
        if (product == null) {
            return null;
        }
        
        // 2. 获取并设置库存信息
        Long stock = inventoryService.getStock(query.getId());
        product.setStock(stock);
        
        // 3. 获取并设置价格信息（可能包含促销价等）
        Long price = priceService.getCurrentPrice(query.getId());
        product.setPrice(price);
        
        return product;
    }
}
```

## 4. 基础设施实现 (infra层)

### 4.1 Repository 实现
```java
package com.ark.center.product.infra.product.repository;

import com.ark.center.product.domain.product.Product;
import com.ark.center.product.infra.product.dao.ProductMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductRepository {
    
    private final ProductMapper productMapper;
    private final RedisTemplate<String, Product> redisTemplate;
    
    private static final String CACHE_KEY = "product:info:";
    
    public Product selectById(Long id) {
        // 1. 查询缓存
        String key = CACHE_KEY + id;
        Product product = redisTemplate.opsForValue().get(key);
        if (product != null) {
            return product;
        }
        
        // 2. 查询数据库
        product = productMapper.selectById(id);
        
        // 3. 写入缓存
        if (product != null) {
            redisTemplate.opsForValue().set(key, product, 1, TimeUnit.HOURS);
        }
        
        return product;
    }
    
    public List<Product> selectByIds(List<Long> ids) {
        // 1. 批量查询缓存
        List<String> keys = ids.stream()
            .map(id -> CACHE_KEY + id)
            .collect(Collectors.toList());
            
        List<Product> products = redisTemplate.opsForValue().multiGet(keys);
        
        // 2. 查询未命中的数据
        List<Long> missIds = new ArrayList<>();
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i) == null) {
                missIds.add(ids.get(i));
            }
        }
        
        if (!missIds.isEmpty()) {
            List<Product> dbProducts = productMapper.selectBatchIds(missIds);
            // 3. 写入缓存
            for (Product product : dbProducts) {
                String key = CACHE_KEY + product.getId();
                redisTemplate.opsForValue().set(key, product, 1, TimeUnit.HOURS);
            }
            // 4. 合并结果
            products.addAll(dbProducts);
        }
        
        return products;
    }
}
```

### 4.2 Service 实现
```java
package com.ark.center.product.infra.product.service;

import com.ark.center.product.client.product.dto.ProductDTO;
import com.ark.center.product.domain.product.Product;
import com.ark.center.product.infra.product.repository.ProductRepository;
import com.ark.center.product.infra.product.convertor.ProductConvertor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    
    private final ProductRepository productRepository;
    private final ProductConvertor productConvertor;
    private final ProductValidator productValidator;
    
    public ProductDTO getProductBase(Long id) {
        // 1. 参数校验
        productValidator.validateId(id);
        
        // 2. 查询商品
        Product product = productRepository.selectById(id);
        if (product == null) {
            return null;
        }
        
        // 3. 业务规则校验
        productValidator.validateStatus(product);
        
        // 4. 业务处理
        enrichProductInfo(product);
        
        // 5. 转换并返回DTO
        return productConvertor.toDTO(product);
    }
    
    public List<ProductDTO> getProductList(List<Long> ids) {
        // 1. 参数校验
        productValidator.validateIds(ids);
        
        // 2. 批量查询
        List<Product> products = productRepository.selectByIds(ids);
        
        // 3. 业务规则过滤
        products = products.stream()
            .filter(product -> productValidator.isValid(product))
            .collect(Collectors.toList());
            
        // 4. 业务处理
        products.forEach(this::enrichProductInfo);
        
        // 5. 批量转换
        return productConvertor.toDTO(products);
    }
    
    private void enrichProductInfo(Product product) {
        // 补充商品其他信息
        product.calculateSaleInfo();
        product.updateHotScore();
        // ...其他业务逻辑
    }
}
```

主要职责划分：

1. Repository:
   - 封装数据访问细节
   - 管理缓存
   - 提供统一的数据访问接口
   - 不包含业务逻辑

2. Service:
   - 实现具体业务逻辑
   - 参数校验和业务规则校验
   - 调用 Repository 访问数据
   - 数据转换和组装
   - 处理业务异常

```java

package com.ark.center.product.infra.inventory.service;

@Service
@RequiredArgsConstructor
public class InventoryService {
    
    private final InventoryRepository inventoryRepository;
    
    public Long getStock(Long productId) {
        // 1. 查询实时库存
        Inventory inventory = inventoryRepository.selectByProductId(productId);
        if (inventory == null) {
            return 0L;
        }
        
        // 2. 计算可用库存
        return inventory.getTotalStock() - inventory.getLockStock();
    }
}

package com.ark.center.product.infra.price.service;

@Service
@RequiredArgsConstructor
public class PriceService {
    
    private final PriceRepository priceRepository;
    private final PromotionService promotionService;
    
    public Long getCurrentPrice(Long productId) {
        // 1. 获取商品基础价格
        Price price = priceRepository.selectByProductId(productId);
        if (price == null) {
            throw new BusinessException("商品价格不存在");
        }
        
        // 2. 计算促销价格
        Long promotionPrice = promotionService.calculatePromotionPrice(productId, price.getPrice());
        
        // 3. 返回最终价格
        return promotionPrice != null ? promotionPrice : price.getPrice();
    }
}