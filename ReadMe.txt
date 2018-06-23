[币世界]

前置工作 ：
    创建crawler数据库
    导入bishijie*.sql

启动方式 ：
    nohup java -jar bishijie-1.0.jar --spring.profiles.active=release >> bishijie.out &

ps:
    爬虫10分钟启动一次；
    刷新关键词线程20分钟启动一次