package edu.zju.gis.dldsj.server.entity;

import edu.zju.gis.dldsj.server.model.JsonAble;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Keran Sun (katus)
 * @version 1.0, 2020-10-05
 */
@Getter
@Setter
public class TaskRecord implements JsonAble {
    private String applicationId;
    private String toolId;
    private String createUser;
    private String submitTime;
    private String finishTime;
    private String state;
    private String resultAddress;
    private String remarks;
    private String outputType;
    private String params;

    @Override
    public String id() {
        return applicationId;
    }
}
