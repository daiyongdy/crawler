使用流程:
    1)  首先导入crawler.sql(其中包含结构与数据)
    2） 在application-release.properties中修改mysql连接信息
    3） maven 打包 命令如下
        mvn clean install -Dmaven.test.skip=true
        打包后会在target文件夹下发现 crawler-1.0.jar 文件
    4） 启动爬虫 命令如下
        nohup java -jar [jvm参数] --spring.profiles.active=release >> log.out &
        爬虫日志会打印在log.out中

    首先在coin表中加入需要爬取的币，爬虫启动后就会自动去 微博、微信、推特中去爬取相关信息

TK 代理:

    地址：http://www.tkdaili.com/
    用户名：【 3736EC42C98687DA282288ED70A33F71 】  默认没有密码，登录时直接输入用户名，即可登录。   IP保护密码为 【 014B59 】 请保管好您的用户名，合法使用代理服务！
    tis： 目前购买了每月50元/35w 代理ip，每月找客服续交，按目前调用频率与ip失效时间 目前可用。

主意事项：
   1.   微博与推特的内容有很多表情 所以需要mysql数据库做特殊设置，具体设置方法如下
        在my.cnf上修改如下：
        ------------------my.cnf------------------------------------------------------
        # For advice on how to change settings please see
        # http://dev.mysql.com/doc/refman/5.6/en/server-configuration-defaults.html
        [client]
        default-character-set=utf8mb4
        [mysql]
        default-character-set = utf8mb4
        # Remove leading # and set to the amount of RAM for the most important data
        # cache in MySQL. Start at 70% of total RAM for dedicated server, else 10%.
        # innodb_buffer_pool_size = 128M

        # Remove leading # to turn on a very important data integrity option: logging
        # changes to the binary log between backups.
        # log_bin
        # These are commonly set, remove the # and set as required.
        # basedir = .....
        # datadir = .....
        # port = .....
        # server_id = .....
        # socket = .....

        # Remove leading # to set options mainly useful for reporting servers.
        # The server defaults are faster for transactions and fast SELECTs.
        # Adjust sizes as needed, experiment to find the optimal values.
        # join_buffer_size = 128M
        # sort_buffer_size = 2M
        # read_rnd_buffer_size = 2M

        sql_mode=NO_ENGINE_SUBSTITUTION,STRICT_TRANS_TABLES
        log-error=/var/log/mysqld.log
        long_query_time=3

        [mysqld]
        character-set-client-handshake = FALSE
        character-set-server = utf8mb4
        collation-server = utf8mb4_unicode_ci
        init_connect='SET NAMES utf8mb4'

        #log-slow-queries= /usr/local/mysql/log/slowquery.log
         ------------------------------------------------------------------------
        重启mysql服务
   2.   由于微信封ip比较严重 所以微信使用ip代理访问，微博和推特还是使用正常ip访问(如果发现封禁 则修改为使用代理方式)。