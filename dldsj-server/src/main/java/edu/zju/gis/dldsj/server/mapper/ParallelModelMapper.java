package edu.zju.gis.dldsj.server.mapper;

import edu.zju.gis.dldsj.server.entity.ParallelModel;
import edu.zju.gis.dldsj.server.entity.ParallelModelUsage;
import edu.zju.gis.dldsj.server.entity.ParallelModelWithBLOBs;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Keran Sun (katus)
 * @version 1.0, 2020-09-27
 */
public interface ParallelModelMapper {
    ParallelModelWithBLOBs selectByPrimaryKey(String artifactId);

    int deleteByPrimaryKey(String artifactId);

    @Select("select * from tb_paralleltool")
    List<ParallelModel> selectAll();

    @Select("select * from tb_paralleltool where USAGES = #{type}")
    List<ParallelModel> selectByType(String type);

    @Select("select * from tb_paralleltool where USER_ID = #{userId}")
    List<ParallelModel> selectByUser(String userId);

    //    @Select("select * from tb_paralleltool where ARTIFACT_ID LIKE #{keyword} OR NAME LIKE #{keyword} OR DESCRIPTION LIKE #{keyword}")
    List<ParallelModel> searchByKeywords(List<String> keywords);

    @Select("select * from tb_paralleltoolusage")
    List<ParallelModelUsage> getAllUsages();

    int insertSelective(ParallelModelWithBLOBs model);
}
