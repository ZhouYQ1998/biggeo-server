package edu.zju.gis.dldsj.server.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yanlo yanlong_lee@qq.com
 * @version 1.0 2018/08/01
 */
@Component
@ConfigurationProperties(prefix = "settings")
@ToString
@Getter
@Setter
public class CommonSetting {
    private List<String> frontEndRegionList;
    private String sessionMaxAge;
}
