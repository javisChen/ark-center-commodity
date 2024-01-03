@echo off

REM 获取传入的模块名称参数
set "module=%1"

set "project=ark-center-iam"
set "package=com\ark\center\iam"

mkdir "%project%-adapter\src\main\java\%package%\adapter\%module%"
mkdir "%project%-adapter\src\main\java\%package%\adapter\%module%\web"
mkdir "%project%-adapter\src\main\java\%package%\adapter\%module%\consumer"

mkdir "%project%-client\src\main\java\%package%\client\%module%"
mkdir "%project%-client\src\main\java\%package%\client\%module%\command"
mkdir "%project%-client\src\main\java\%package%\client\%module%\query"
mkdir "%project%-client\src\main\java\%package%\client\%module%\dto"

mkdir "%project%-domain\src\main\java\%package%\domain\%module%"
mkdir "%project%-domain\src\main\java\%package%\domain\%module%\gateway"
mkdir "%project%-domain\src\main\java\%package%\domain\%module%\service"
mkdir "%project%-domain\src\main\java\%package%\domain\%module%\vo"

mkdir "%project%-infra\src\main\java\%package%\infra\%module%"
mkdir "%project%-infra\src\main\java\%package%\infra\%module%\gateway\db\xml"
mkdir "%project%-infra\src\main\java\%package%\infra\%module%\gateway\impl"
mkdir "%project%-infra\src\main\java\%package%\infra\%module%\assembler"

mkdir "%project%-app\src\main\java\%package%\application\%module%"
mkdir "%project%-app\src\main\java\%package%\application\%module%\executor"
