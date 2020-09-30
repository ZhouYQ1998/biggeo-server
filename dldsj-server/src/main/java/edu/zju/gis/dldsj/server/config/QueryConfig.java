package edu.zju.gis.dldsj.server.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author yanlong_lee@qq.com
 * @version 1.0 2018/10/23
 */
@Component
@ConfigurationProperties(prefix = "query")
@Getter
@Setter
public class QueryConfig {
    private String jar;
    private String mainClass;//执行数据查询的spark程序主类
    private String driverMemory;
    private String executorMemory;
    private Integer numExecutors;
    private Integer executorCores;
    private Integer parallelism;
    /**
     * statistics type -> statistics model and other runtime config<br/>
     * e.g. basic(including sum,count,mean,min,max,variance) -> mainClass, outFields, driverMemory, etc.
     */
    private Map<String, Map<String, String>> statistics;
}
