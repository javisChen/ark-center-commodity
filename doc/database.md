# 会员中心数据库设计

## 会员账户相关表

### 1. 会员基础信息表(t_member)
```sql
CREATE TABLE `t_member` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `mobile` varchar(11) NOT NULL COMMENT '手机号',
    `nickname` varchar(32) DEFAULT NULL COMMENT '昵称',
    `avatar` varchar(255) DEFAULT NULL COMMENT '头像URL',
    `gender` tinyint(4) DEFAULT 0 COMMENT '性别 0-未知 1-男 2-女',
    `birth_date` date DEFAULT NULL COMMENT '出生日期',
    `level` int(11) NOT NULL DEFAULT 0 COMMENT '会员等级',
    `growth_value` bigint(20) NOT NULL DEFAULT 0 COMMENT '成长值',
    `points` bigint(20) NOT NULL DEFAULT 0 COMMENT '积分',
    `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态 1-正常 0-禁用',
    `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '更新时间',
    `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    `creator` bigint unsigned DEFAULT '0' NOT NULL COMMENT '创建人',
    `modifier` bigint unsigned DEFAULT '0' NOT NULL COMMENT '更新人',
    `is_deleted` bigint unsigned DEFAULT '0' NOT NULL COMMENT '删除标识 0-表示未删除 大于0-已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_mobile` (`mobile`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员基础信息表';
```
### 2. 会员认证信息表(t_member_auth)
```sql
CREATE TABLE `t_member_auth` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `member_id` bigint(20) NOT NULL COMMENT '会员ID',
    `identity_type` tinyint(4) NOT NULL COMMENT '认证类型 1-手机号 2-邮箱 3-用户名',
    `identifier` varchar(128) NOT NULL COMMENT '认证标识(手机号/邮箱/用户名)',
    `credential` varchar(256) NOT NULL COMMENT '密码凭证',
    `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态 1-正常 0-禁用',
    `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '更新时间',
    `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    `creator` bigint unsigned DEFAULT '0' NOT NULL COMMENT '创建人',
    `modifier` bigint unsigned DEFAULT '0' NOT NULL COMMENT '更新人',
    `is_deleted` bigint unsigned DEFAULT '0' NOT NULL COMMENT '删除标识 0-表示未删除 大于0-已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_member_identity` (`member_id`,`identity_type`),
    UNIQUE KEY `uk_identifier` (`identity_type`,`identifier`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员认证信息表';
```