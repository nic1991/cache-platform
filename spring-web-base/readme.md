spring-web-base

一.简介
    spring-web-base是基于springboot开发的一款简易版的管理系统模板（主要包含，用户管理，角色管理，菜单管理，权限管理等功能），
项目以jar包方式服务。使用它我们可以迅速搭建我们的管理系统，我们可以把全部精力投入我们的功能代码实现，
而不需要做这些重复的工作，我们的代码模块也更加清晰，便于我们管理。


二.spring-web-base 示例启动步骤

1.初始化mysql数据库:
  a.创建数据库serverbase
  b.执行serverbase.sql 脚本导入表及数据

2.启动服务:
  a.修改配置文件application.yml 设置数据库相关配置
  b.执行WebBase main函数启动服务

3.默认访问URL:http://localhost:8008/rest/pages/index

三.使用Spring-web-base 快速搭建管理系统

  1.创建数据库并初始化

  2.创建你自己的springboot 项目

  3.引入spring-web-base jar包

  4.在你的springboot项目启动类上注解添加扫描路径如（com.newegg.ec.base 与 com.newegg.ec.base.dao.mysql 需要在 component 与 mapper 路径中添加进来）:
    @ComponentScan(basePackages={"com.newegg.ec.base","com.newegg.ec.cache"})
    @MapperScan(basePackages={"com.newegg.ec.base.dao.mysql","com.newegg.ec.cache.dao"})

  5.修改配置文件application.yml，配置文件如下(主要是设置数据库连接相关的配置修改成你的建库地址):
  server:
    tomcat.uri-encoding: UTF-8
    port: 8181

  spring:
    application:
        name: machine-manager
    http:
        encoding:
          enabled: true
          force: true
          charset: UTF-8
    datasource:
        name: serverbase
        driver-class-name: com.mysql.jdbc.Driver
        url: jdbc:mysql://ssbigdata02:4000/cacheplatform?useUnicode=true&characterEncoding=utf-8
        username: xxx
        password:

  mybatis:
    mapper-locations: classpath*:mappers/*.xml

  http:
     maxTotal: 10
     defaultMaxPerRoute: 10
     connectTimeout: 5000
     connectionRequestTimeout: 5000
     socketTimeout: 3000
     staleConnectionCheckEnabled: true

  6.启动项目，并访问:http://localhost:8181/rest/pages/index

四.功能说明

  1.默认系统用户是root ， 初始密码是123456

  2.初始菜单有system ，menuManage，urlManage，roleManage，userManage

  3.system是父文件夹

  4.menuManage 是用来管理菜单的，我们可以新建菜单，配置相应的路由，设置它的位置及样式

  5.urlManage 用来管理我们系统需要限制访问的rest接口，如果我们需要对某接口加上权限控制，那么我们需要把接口在这里加上

  6.roleManage为我们系统设置角色，设置角色时我们需要指定哪些菜单他是可见的，哪些rest接口他是可以访问的

  7.usermanage这里是我们用户管理模块，用户的增删改查，及给用户分配角色，那么用户也就获得了这些角色所拥有的权限。

五.这个项目功能很精简，但能快速帮我们搭建起我们的管理系统框架，赶快去体验吧！
