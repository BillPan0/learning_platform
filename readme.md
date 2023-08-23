## 代码说明

LearningPlatform工程基于Spring，包括：

CommonModule（公共工具模块）、DaoModule（数据持久化模块）、ServiceModule（服务处理模块）、WebModule（web接口模块）、DubboInterface（Dubbo RPC接口模块）和WebSSHClient（终端分配及连接管理模块）六个子模块，

其中WebModule和WebSSHClient包含启动程序入口，其余为依赖模块。

## 运行架构

WebModule通过HTTP方式与前端进行交互。

### 终端分配环节
前端首先通过HTTP向WebModule发送终端分配请求，WebModule再通过RPC方式调用WebSSHClient的功能函数并获取到返回的终端信息交回给前端；
### 终端命令模拟环节
前端通过Websocket同WebModule进行交互，WebModule再将前端需要发送的终端指令通过消息队列kafka发送至WebSSHClient，WebSSHClient通过SSH连接方式将命令发送至终端从而获取执行结果，最终同样将执行结果通过消息队列写回，WebModule从消息队列中得到执行结果后将其返回给前端，完成终端命令模拟业务逻辑。

## 配置项

WebModule用maven打包后运行于任意主机A，WebSSHClient打包后需运行于提供docker compose服务的Linux主机或虚拟机。

### 1）

```shell
LearrningPlatform/WebModule/src/main/resources/application.properties
```

中的`${spring.provider.host}`修改为各服务（数据库、zookeeper、Kafka等）部署的主机ip；`${spring.web-module.runner.host}`修改为主机A能与上述服务连通的网卡的ip。

### 2）

```shell
LearrningPlatform/WebSSHClient/src/main/resources/config/application.yml
```

中的`${spring.provider.host}`修改为各服务（数据库、zookeeper、Kafka等）部署的主机ip，同 1）。

### 3）

```shell
LearrningPlatform/CustomizedResource/docker-compose-kafkas.yml
```

中`KAFKA_CFG_ADVERTISED_LISTENERS=PLAINTEXT://192.168.170.129:19093`的192.168.170.129修改为该服务部署主机可对外提供连接的网卡ip。

## 运行

对应文件夹下执行：

`java -jar WebModule-0.0.1-SNAPSHOT.jar`

`java -jar WebSSHClient-1.0-SNAPSHOT.jar`

`docker compose -f docker-compose-kafkas.yml up -d`

## 镜像导入

1）将镜像拷贝至目的主机；

2）数据库表`image_info`下写入镜像信息。

3）数据库表`pdf_to_image_map`下写入镜像与pdf映射关系信息。