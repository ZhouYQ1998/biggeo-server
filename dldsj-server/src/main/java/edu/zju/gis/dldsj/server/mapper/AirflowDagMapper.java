package edu.zju.gis.dldsj.server.mapper;

import edu.zju.gis.dldsj.server.entity.workflow.AirflowDag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AirflowDagMapper {
    int deleteByPrimaryKey(String dagId);

    int insert(AirflowDag record);

    int insertSelective(AirflowDag record);

    AirflowDag selectByPrimaryKey(String dagId);

    int updateByPrimaryKeySelective(AirflowDag record);

    int updateByPrimaryKey(AirflowDag record);

    int selectCount();

    List<AirflowDag> selectByPage(@Param("offset") int offset, @Param("size") int size);
}
