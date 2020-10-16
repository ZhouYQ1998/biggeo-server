package edu.zju.gis.dldsj.server.mapper;


import edu.zju.gis.dldsj.server.entity.workflow.WorkFlowDag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WorkFlowDagMapper  {
    int deleteByPrimaryKey(String id);

    int deleteByNameAndUserId(String name,String userId);

    int insert(WorkFlowDag record);

    int insertSelective(WorkFlowDag record);

    WorkFlowDag selectByPrimaryKey(String id);

    WorkFlowDag selectByNameAndUserId(String name,String userId);

    int updateByPrimaryKeySelective(WorkFlowDag record);

    int updateByPrimaryKey(WorkFlowDag record);

    List<WorkFlowDag> selectByUserId(String userId);

    List<WorkFlowDag> searchByDagName(@Param("name") String name);
}
