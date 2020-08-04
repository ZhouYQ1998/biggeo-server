package edu.zju.gis.dldsj.server.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "application-info")
@ToString
public class ApplicationConfigs {

    @Setter
    @Getter
    private String name;

    @Setter
    @Getter
    private String version;

    @Setter
    @Getter
    private String release;

    @Setter
    @Getter
    private String authority;


}
