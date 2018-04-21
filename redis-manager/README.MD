# RedisManager
> redis manager  spring jpa， 页面共用模块设计

```  代码结构
├── README.MD
├── docs                     // 文档目录
│   └── redis-manager.sql    // 关联的 sql 
├── pom.xml
├── src.main.java.com.newegg.ec.cache
    └── becnckend            // 后台任务，包括定时检测集群问题task,redis monitor 获取
    └── component            // 一个是节点管理组件，另一个是 redis 管理组件  
    └── controller           // 程序路由接口      
    └── dao                  // 数据处理层
    └── logic                // 逻辑处理层 
    └── model                // 实体层
    └── plugin               // 插件层 
        └── docker           // docker 相关
        └── humback          // humback 相关 
        └── machine          // 物理机相关
    └── Application      //程序入口  

```