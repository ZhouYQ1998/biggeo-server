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
    private String id;

    private String name;

    private String userid;

    private String status;

    private Date starttime;

    private Date endtime;
}
