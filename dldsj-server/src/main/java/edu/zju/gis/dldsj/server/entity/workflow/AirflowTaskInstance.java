package edu.zju.gis.dldsj.server.entity.workflow;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author Jiarui
 * @date 2020/9/24
 */

@Getter
@Setter
@ToString
public class AirflowTaskInstance {
    private String taskId;
    private String dagId;
    private Date executionDate;
    private Date startDate;
    private Date endDate;
    private Float duration;
    private String state;
    private Integer tryNumber;
    private String hostname;
    private String unixname;
    private Integer jobId;
    private String pool;
    private String queue;
    private Integer priorityWeight;
    private String operator;
    private Date queuedDttm;
    private Integer pid;
    private Integer maxTries;
}
