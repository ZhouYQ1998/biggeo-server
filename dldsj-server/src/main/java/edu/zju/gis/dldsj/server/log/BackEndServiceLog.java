package edu.zju.gis.dldsj.server.log;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author Hu
 * @date 2019/4/27
 * 后台管理请求日志
 **/
@Getter
@Setter
public class BackEndServiceLog extends ServiceLog {

    // 请求人ID
    private String id;

    // 请求路径
    private String url;

    // 请求参数
    private String params;

    // 请求方法
    private String method;

    // 请求类型
    private String requestType;

    // 请求客户端IP
    private String clientIp;

    // 请求返回码
    private int status;

    // 请求返回时间
    private Date endTime;

    // 请求开始时间
    private Date startTime;

    // 请求时间差
    private long interval;

    // 请求体
    private String requestBody;

    // 返回状态
    private int responseStatus;

    // 返回体
    private String responseBody;

}
