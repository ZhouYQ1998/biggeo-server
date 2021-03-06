package edu.zju.gis.dldsj.server.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yanlo yanlong_lee@qq.com
 * @version 1.0 2018/08/01
 * generated by mysql-generator
 */
@Getter
@Setter
public class ParallelModelWithBLOBs extends ParallelModel {
    /**
     * 算法描述
     */
    private String algo;

    /**
     * 输入数据规范，支持从多个地方输入数据，设置不同的data标签，自定义data标签名称。例如:<br>
     * [{"path":"hdfs","type":"txt","fields":[{"name":"","datatype":"","default":"","description":"","required":"true"}]}]
     */
    private String input;
    /**
     * 输出数据规范，支持多个地方输出数据，设置不同的data标签。例如:<br>
     * [{"path":"file","type":"txt","fields":[{"name":"","datatype":"","default":"","description":"","required":"true"}]}]
     */
    private String out;

    /**
     * 输入参数，是一个json格式的字符串。例如：<br/>
     * [{"name":"property_1","datatype":"string","default":"","description":""},
     * {"name":"property_2","datatype:"integer","default":10,"minimum":0,"maximum":100,"description":""}]
     */
    private String parameters;
    /**
     * 第三方库依赖，json格式的字符串。例如：<br/>
     * [{"name":"json","version":"20180130","url":"https://github.com/stleary/JSON-java"}
     */
    private String packages;
    /**
     * 模型主要实现接口，对外提供的函数,json格式的字符串。例如：<br/>
     * [{"name":"","intfcClass":"","intfdesc":"","inParams":[{"name":"","datatype":"","default":"","description":""}],
     * "outParams":[{"name":"","datatype":"","default":"","description":""}]}]
     */
    private String interfaces;
    /**
     * 程序测试结果,json格式的字符串。例如：<br/>
     * {"in":"","inDesc":"","out":"","cmdline":"","outDesc":""}
     */
    private String test;

}
