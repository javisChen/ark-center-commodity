# 介绍
商品中心

# 运行

## Docker Build
```shell
docker build -f ./ark-center-product-start/Dockerfile -t ark-center-product:v1 ./ark-center-product-start
```
## Docker Run
```shell
docker run -p 8083:8083 --name ark-center-product -d \
-e NACOS_DISCOVERY_IP=172.24.80.20 \
-e NACOS_DISCOVERY_SERVER_ADDR=172.24.80.20:8848 \
-e NACOS_CONFIG_SERVER_ADDR=172.24.80.20:8848 \
-e SYS_OPT=-DSpring.profiles.active=dev \
ark-center-product:v1
```

# 软件架构

## 组件说明

| 组件名称                         | 描述           | 版本    |
|------------------------------|--------------|-------|
| ark-center-product-acl     | 防腐层          | 1.0.0 |
| ark-center-product-api     | 开放接口层        | 1.0.0 |
| ark-center-product-manager | 通用业务处理层      | 1.0.0 |
| ark-center-product-service | 具体业务以及内部访问转发 | 1.0.0 |
| ark-center-product-start   | 启动层          | 1.0.0 |


## 使用说明
```
mvn install到本地（如果不想运行测试，可以加上-DskipTests参数）
```

### 表设计

brand（品牌）

| 字段名称      | 说明      | 类型      | 长度     | 备注  |
|-----------|---------|---------|--------|-----|
| name      | 品牌名称    | varchar | (100)  |     |
| image_url | 品牌图标url | varchar | (1000) |     |
| letter    | 首字母     | char    |        |     |
| seq       | 排序      | int     |        |     |

category（类目）

| 字段名称          | 说明                  | 类型         | 长度     | 备注  |
|---------------|---------------------|------------|--------|-----|
| name          | 分类名称                | varchar    | (50)   |     |
| commodity_num | 商品数量                | int        | (1000) |     |
| is_show       | 是否显示 enums[否,0;是,1] | tinyint(1) | 1      |     |
| is_menu       | 是否导航 enums[否,0;是,1] | tinyint(1) | 1      |     |
| seq           | 排序                  | int        |        |
| parent_id     | 上级ID                | int        |        |
| template_id   | 模板ID                | int        |        |


template（类目）

| 字段名称          | 说明                  | 类型         | 长度     | 备注  |
|---------------|---------------------|------------|--------|-----|
| name          | 分类名称                | varchar    | (50)   |     |
| commodity_num | 商品数量                | int        | (1000) |     |
| is_show       | 是否显示 enums[否,0;是,1] | tinyint(1) | 1      |     |
| is_menu       | 是否导航 enums[否,0;是,1] | tinyint(1) | 1      |     |
| seq           | 排序                  | int        |        |
| parent_id     | 上级ID                | int        |        |
| template_id   | 模板ID                | int        |        |