package edu.zju.gis.dldsj.server.mapper;


import edu.zju.gis.dldsj.server.entity.workflow.WorkFlowDag;

import java.util.List;

public interface WorkFlowDagMapper  {
    int deleteByPrimaryKey(String id);

    int insert(WorkFlowDag record);

    int insertSelective(WorkFlowDag record);

    WorkFlowDag selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WorkFlowDag record);

    int updateByPrimaryKey(WorkFlowDag record);

    List<WorkFlowDag> selectByUserId(String userId);
}
