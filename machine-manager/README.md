# MachineManager
> MachineManager linux 机器监控平台，可以动态添加机器不需要在机器上安装agent,包含了大部分常用的监控项如:内存 cpu io 磁盘等。

## 一、主要特性
1. MachineManager 包含了大部分的运维所需要的监控指标，并且支持七天范围内的历史数据查询
2. MachineManager 实现原理是通过 ssh 去获取机器的监控信息，所以架构上更简单不需要安装 agent
3.

## 二、安装方式
> MachineManager 是一个 web 项目，所以他除了包含依赖的 jar 包文件，还会依赖一些 resource(html,css 配置等)，所以您可以参考以下过程进行安装

### 2.1 安装准备
> 安装过程需要的各种依赖项

1. cd ${you_project_dir} 通过终端进入到你的工程目录
2. mvn dependency:copy-dependencies -DoutputDirectory=target/lib -DincludeScope=runtime  生成 jar 包，这个时候您可以到 target 目录查看到 lib 包
3. mvn package  将 MachineManager 打成 jar 包

### 2.2 部署
1. 在您的服务器上新建目录 /opt/app/machine-manager
2. 生成如下目录
```
├── /opt/app/machine-manager
      └── bin  这个文件您可以在上层目录上找到，您只需要修改对应的类名
      └── conf 这个是您项目的 resource 文件
      └── lib 这个是上面过程打的 lib 包
      └── logs 日志目录
      └── machine-manager.jar 这个是 MachineManager 的 jar 包
```
3. 启动服务 ./bin/start.sh 这个时候您可以 http://*****:***/rest/pages/main 查看

## 三、操作方式

1 http://*****:***/rest/pages/main 查看
2 如图
<img src="/docs/images/info.png" />



