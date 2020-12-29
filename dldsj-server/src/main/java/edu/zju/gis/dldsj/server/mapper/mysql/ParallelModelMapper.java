package edu.zju.gis.dldsj.server.mapper.mysql;

import edu.zju.gis.dldsj.server.entity.ParallelModel;
import edu.zju.gis.dldsj.server.entity.ParallelModelUsage;
import edu.zju.gis.dldsj.server.entity.ParallelModelWithBLOBs;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Keran Sun (katus)
 * @version 1.0, 2020-09-27
 */
public interface ParallelModelMapper {
    ParallelModelWithBLOBs selectByPrimaryKey(String artifactId);

    int deleteByPrimaryKey(String artifactId);

    List<ParallelModel> selectAll();

    List<ParallelModel> selectByType(@Param("type") String type);

    List<ParallelModel> selectByUser(@Param("userId") String userId);

    //    @Select("select * from tb_paralleltool where ARTIFACT_ID LIKE #{keyword} OR NAME LIKE #{keyword} OR DESCRIPTION LIKE #{keyword}")
    List<ParallelModel> searchByKeywords(List<String> keywords);

    @Select("select * from tb_paralleltoolusage")
    List<ParallelModelUsage> getAllUsages();

    int insertSelective(ParallelModelWithBLOBs model);
}
