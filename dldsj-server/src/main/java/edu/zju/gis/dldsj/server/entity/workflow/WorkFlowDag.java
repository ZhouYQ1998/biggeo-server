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
public class WorkFlowDag {
    private String id;
    private String name;
    private String description;
    private String userId;
    private String connections;
    private String nodes;
    private String style;
    private Date createdTime;
    private Date lastModifyTime;
}
