package edu.zju.gis.dldsj.server;

import edu.zju.gis.dldsj.server.task.SpiderMonitorTasks;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAsync
@EnableTransactionManagement
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, MybatisAutoConfiguration.class})

public class DldsjApplication {
    private static Logger logger = LoggerFactory.getLogger(DldsjApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(DldsjApplication.class, args);
        SpiderMonitorTasks task = new SpiderMonitorTasks();
        task.run();
    }
}
