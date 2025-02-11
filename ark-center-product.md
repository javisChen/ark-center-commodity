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

```java:ark-center-product-client/src/main/java/com/ark/center/product/client/product/ProductQueryApi.java
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

```java:ark-center-product-client/src/main/java/com/ark/center/product/client/product/dto/ProductDTO.java
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

```java:ark-center-product-client/src/main/java/com/ark/center/product/client/product/query/ProductQuery.java
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

```java:ark-center-product-adapter/src/main/java/com/ark/center/product/adapter/product/web/ProductController.java
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

## 3. 业务逻辑处理 (app层)

```java:ark-center-product-app/src/main/java/com/ark/center/product/app/product/ProductQueryService.java
package com.ark.center.product.app.product;

import com.ark.center.product.client.product.dto.ProductDTO;
import com.ark.center.product.client.product.query.ProductQuery;
import com.ark.center.product.infra.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductQueryService {
    
    private final ProductService productService;
    
    public ProductDTO getProduct(ProductQuery query) {
        return productService.getProduct(query.getId());
    }
}
```

## 4. 基础设施实现 (infra层)

```java:ark-center-product-infra/src/main/java/com/ark/center/product/infra/product/service/ProductService.java
package com.ark.center.product.infra.product.service;

import com.ark.center.product.client.product.dto.ProductDTO;
import com.ark.center.product.infra.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    
    private final ProductRepository productRepository;
    
    public ProductDTO getProduct(Long id) {
        // 实际业务逻辑省略
        return new ProductDTO();
    }
}
```

```java:ark-center-product-infra/src/main/java/com/ark/center/product/infra/product/repository/ProductRepository.java
package com.ark.center.product.infra.product.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductRepository {
    // 数据访问实现省略
}
``` 