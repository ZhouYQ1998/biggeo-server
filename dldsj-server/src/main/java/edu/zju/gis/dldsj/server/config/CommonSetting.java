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

    //存储可视化部分工程json文件、数据json文件以及一些图片
    private String mapfileSavepath;

    //原来工程中的设置
    private String parallelFilePath;
    private String jarPath;
    private String xmlPath;
    private String templatePath;
    private String picPath;
    private String geoDataPath;
    private String metaPath;
    private String nameNode;
    private String username;
    private String password;
    private String javaHome;
    private String hadoopHome;
    private String sparkHome;
    private String esHost;
    private int esPort;
    private String esName;
    private String esIndex;
    private String jobMonitor;
    private int monitorInterval;
    private int jobFailInterval;
    private String jobResultPath;
    private int initTaskPoolSize;
    private int maxTaskPoolSize;
    private int keepAliveSeconds;
    //    private String airflowHome;
    private String dagsFolder;
    private List<String> frontEndRegionList;
    private String sessionMaxAge;
    private String userSpaceRootPath;
    private String calculateDataNameSpace;
    private String selfDataNameSpace;
    private String systemPublicAccount;
    private String rasterToolTempDir;
    private String topic3ToolTempDir;
    private String exportToolJarClass;
    private String exportToolJarPath;
    private String downloadTempDirectory;
    private String importToolJarClass;
    private String importToolJarPath;
    private String airFlowHome;
    private String airFlowDagsPath;
    private String hdFsUri;
    private String lFsUri;


}
