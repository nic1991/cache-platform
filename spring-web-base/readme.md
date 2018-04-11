this is  spring-web-base

spring-web-base 启动步骤:

1.初始化mysql数据库:
  a.创建数据库serverbase
  b.执行serverbase.sql 脚本导入表及数据

2.启动服务:
  a.修改配置文件application.yml 设置数据库相关配置
  b.执行WebBase main函数启动服务

3.默认访问URL:http://localhost:8008/rest/pages/index