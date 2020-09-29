package edu.zju.gis.dldsj.server.mapper;


import edu.zju.gis.dldsj.server.entity.workflow.WorkFlowJob;

import java.util.List;

public interface WorkFlowJobMapper {
    int deleteByPrimaryKey(String id);

    int insert(WorkFlowJob record);

    int insertSelective(WorkFlowJob record);

    WorkFlowJob selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WorkFlowJob record);

    int updateByPrimaryKey(WorkFlowJob record);

    List<WorkFlowJob> selectByRunId(String runId);
}
