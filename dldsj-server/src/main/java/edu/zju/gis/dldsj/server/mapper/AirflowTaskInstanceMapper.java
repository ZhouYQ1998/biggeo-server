package edu.zju.gis.dldsj.server.mapper;

import edu.zju.gis.dldsj.server.entity.workflow.AirflowTaskInstance;

public interface AirflowTaskInstanceMapper {
    int deleteByPrimaryKey(AirflowTaskInstance key);

    int insert(AirflowTaskInstance record);

    int insertSelective(AirflowTaskInstance record);

    AirflowTaskInstance selectInstance(String dagId, String jobId, long executionDate);
}
