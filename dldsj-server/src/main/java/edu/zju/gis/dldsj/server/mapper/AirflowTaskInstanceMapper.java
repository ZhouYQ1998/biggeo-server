package edu.zju.gis.dldsj.server.mapper;

import edu.zju.gis.dldsj.server.entity.workflow.AirflowTaskInstance;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface AirflowTaskInstanceMapper {
    int deleteByPrimaryKey(AirflowTaskInstance key);

    int insert(AirflowTaskInstance record);

    int insertSelective(AirflowTaskInstance record);

    List<AirflowTaskInstance> selectByDagAndTask(@Param("dagId") String dagId, @Param("taskId") String taskId);

    List<AirflowTaskInstance> selectByDag(@Param("dagId") String dagId);

    int updateByPrimaryKeySelective(AirflowTaskInstance record);

    int updateByPrimaryKey(AirflowTaskInstance record);

    AirflowTaskInstance selectInstance(String dagId, String jobId, Date executionDate);
}
