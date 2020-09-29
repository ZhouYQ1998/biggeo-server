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
public class AirflowDagRun {
    private Integer id;
    private String dagId;
    private Date executionDate;
    private String state;
    private String runId;
    private Boolean externalTrigger;
    private Date endDate;
    private Date startDate;
    private byte[] conf;
}
