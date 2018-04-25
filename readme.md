# This is cache platform

> Cache Platform 主要是提供一套RedisCluster的管理工具，方便用户对 Redis 2.8.x / RedisCluster3.x 进行监控和管理。
  包括的主要功能有：
  1. 机器管理监控(部署Redis Node的PC负载监控)
  2. Redis 集群的监控
  3. Redis 集群的管理：slot拆分，迁移
  4. 统一的数据导入：将DB进行全量/增量 导入Redis


## 一、CachePlatform介绍
> 主要模块


1. spring-web-base: 一套可以进行Web工程权限控制组件
2. machine-manager：可以对Linux机器进行负载监控的组件：
3. redis-manager:集群管理和监控组件
4. redis-dih: 给Redis导入数据的组件 

> 架构设计

1. 一键部署（可以指定端口），不指定就使用随机可用端口（小）
2. 内存碎片（我认为统计内存碎片大小相对于总内存的比率会更好）（小）
3. 模板配置管理（中）
4. 报警功能（中）
5. 原有的bug解决


> QuickStart


> 使用手册
