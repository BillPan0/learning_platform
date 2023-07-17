## 代码说明

LearningPlatform工程基于Spring，包括：

CommonModule（公共工具模块）、DaoModule（数据持久化模块）、ServiceModule（服务处理模块）、WebModule（web接口模块）、DubboInterface（Dubbo RPC接口模块）和WebSSHClient（终端分配及连接管理模块）六个子模块，

其中WebModule和WebSSHClient包含启动程序入口，其余为依赖模块。

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