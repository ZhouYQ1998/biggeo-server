package edu.zju.gis.dldsj.server.mapper;


import edu.zju.gis.dldsj.server.entity.workflow.WorkFlowRun;

import java.util.List;

public interface WorkFlowRunMapper {
    int deleteByPrimaryKey(String id);

    int insert(WorkFlowRun record);

    int insertSelective(WorkFlowRun record);

    WorkFlowRun selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WorkFlowRun record);

    int updateByPrimaryKey(WorkFlowRun record);

    List<WorkFlowRun> selectByUserId(String userId);
}
