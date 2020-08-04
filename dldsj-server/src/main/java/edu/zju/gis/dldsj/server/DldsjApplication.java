package edu.zju.gis.dldsj.server;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
@MapperScan({"edu.zju.gis.dldsj.server.mapper"})
public class DldsjApplication {
    private static Logger logger = LoggerFactory.getLogger(DldsjApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(DldsjApplication.class, args);
    }
}
