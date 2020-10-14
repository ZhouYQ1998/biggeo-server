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
public class WorkFlowRun {
    private String runId;
    private String name;
    private String userId;
    private String status;
    private Date startTime;
    private Date endTime;
}
