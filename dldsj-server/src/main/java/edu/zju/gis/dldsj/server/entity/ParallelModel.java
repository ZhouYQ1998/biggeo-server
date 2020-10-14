package edu.zju.gis.dldsj.server.entity;

import edu.zju.gis.dldsj.server.model.JsonAble;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Keran Sun (katus)
 * @version 1.0, 2020-09-25
 */
@Getter
@Setter
@NoArgsConstructor
public class ParallelModel implements JsonAble {
    /**
     * xml文件中包含的项目
     */
    private String artifactId;   // 模型标识 首字母大写 驼峰命名法
    private String name;   // 模型名称
    private String description;   // 模型描述
    private String usages;   // 模型适用类型
    private String mainClass;   // 模型主类
    private String frameworkType;   // 模型计算框架 Spark/MapReduce
    private String date;   // 模型开发日期
    private String versionId;   // 模型开发日期
    private String keywords;   // 关键字
    private String groupId;   // 联系单位
    private String authorId;   // 模型负责人
    private String email;   // 电子邮箱地址
    /**
     * 模型文件在服务器上的存储路径
     */
    private String jarPath;
    private String xmlPath;
    private String picPath;
    /**
     * 并行模型运行的性能参数
     */
    private Integer numExecutors;
    private String driverMemory;
    private String executorMemory;
    private Integer executorCores;
    private Integer parallelism;
    /**
     * 权限信息
     */
    private String userId;
    private Boolean isPublic;

    @Override
    public String id() {
        return artifactId;
    }
}
