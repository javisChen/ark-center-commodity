#!/bin/bash

# 获取用户输入的变量
read -p "请输入模块名称：" module

project="ark-center-product"
package="com/ark/center/product"

mkdir -p "${project}-adapter/src/main/java/${package}/adapter/${module}"
mkdir -p "${project}-adapter/src/main/java/${package}/adapter/${module}/web"
mkdir -p "${project}-adapter/src/main/java/${package}/adapter/${module}/consumer"

mkdir -p "${project}-client/src/main/java/${package}/client/${module}"
mkdir -p "${project}-client/src/main/java/${package}/client/${module}/command"
mkdir -p "${project}-client/src/main/java/${package}/client/${module}/query"
mkdir -p "${project}-client/src/main/java/${package}/client/${module}/dto"

mkdir -p "${project}-domain/src/main/java/${package}/domain/${module}"
mkdir -p "${project}-domain/src/main/java/${package}/domain/${module}/gateway"
mkdir -p "${project}-domain/src/main/java/${package}/domain/${module}/service"
mkdir -p "${project}-domain/src/main/java/${package}/domain/${module}/vo"

mkdir -p "${project}-infra/src/main/java/${package}/infra/${module}"
mkdir -p "${project}-infra/src/main/java/${package}/infra/${module}/gateway/db/xml"
mkdir -p "${project}-infra/src/main/java/${package}/infra/${module}/gateway/impl"
mkdir -p "${project}-infra/src/main/java/${package}/infra/${module}/assembler"

mkdir -p "${project}-app/src/main/java/${package}/application/${module}"
mkdir -p "${project}-app/src/main/java/${package}/application/${module}/executor"