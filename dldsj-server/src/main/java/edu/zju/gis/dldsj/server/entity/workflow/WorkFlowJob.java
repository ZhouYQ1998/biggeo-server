package edu.zju.gis.dldsj.server.entity.workflow;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author Jiarui
 * @date 2020/9/24
 */

@Getter
@Setter
public class WorkFlowJob {
    private String id;

    private String runid;

    private String jobid;

    private String name;

    private String model;

    private String params;

    private String status;

    private Date starttime;

    private Date endtime;
}
