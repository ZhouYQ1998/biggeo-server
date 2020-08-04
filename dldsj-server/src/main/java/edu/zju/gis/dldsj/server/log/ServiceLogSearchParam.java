package edu.zju.gis.dldsj.server.log;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author Hu
 * @date 2019/5/6
 * 服务日志查询
 **/
@Getter
@Setter
@ToString
public class ServiceLogSearchParam {

    // 服务类型，分为两种：Manager，System，分别表示后台管理服务日志和系统服务日志
    private String type;

    // 请求人ID
    private String id;

    // 请求客户端IP
    private String clientIp;

    // 请求返回时间
    private Date endTime;

    // 请求开始时间
    private Date startTime;

    // 返回状态
    private int responseStatus;

    // 返回体
    private String responseBody;


    public enum ServiceLogType {

        MANAGER("manager"), SYSTEM("system");

        @Getter
        private String name;

        ServiceLogType(String name) {
            this.name = name;
        }

    }

}
