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
public class AirflowDag {
    private String dagId;
    private Boolean isPaused;
    private Boolean isSubdag;
    private Boolean isActive;
    private Date lastSchedulerRun;
    private Date lastPickled;
    private Date lastExpired;
    private Boolean schedulerLock;
    private Integer pickleId;
    private String fileloc;
    private String owners;
}
