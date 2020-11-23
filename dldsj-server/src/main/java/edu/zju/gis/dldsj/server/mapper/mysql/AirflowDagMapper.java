package edu.zju.gis.dldsj.server.mapper.mysql;

import edu.zju.gis.dldsj.server.entity.workflow.AirflowDag;

public interface AirflowDagMapper {
    int deleteByPrimaryKey(String dagId);

    int insert(AirflowDag record);

    int insertSelective(AirflowDag record);

    AirflowDag selectByPrimaryKey(String dagId);
}
