package edu.zju.gis.dldsj.server.mapper;


import edu.zju.gis.dldsj.server.entity.workflow.AirflowDagRun;

import java.util.List;

public interface AirflowDagRunMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AirflowDagRun record);

    int insertSelective(AirflowDagRun record);

    AirflowDagRun selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AirflowDagRun record);

    int updateByPrimaryKey(AirflowDagRun record);

    List<AirflowDagRun> selectRunningDagById(String dagId);

    AirflowDagRun selectDagRunByTime(String dagId, long executionDate);

    int deleteByDagId(String dagId);
}
