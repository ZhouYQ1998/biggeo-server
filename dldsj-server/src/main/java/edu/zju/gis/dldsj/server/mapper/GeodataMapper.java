package edu.zju.gis.dldsj.server.mapper;

import edu.zju.gis.dldsj.server.base.BaseMapper;
import edu.zju.gis.dldsj.server.entity.Geodata;
import edu.zju.gis.dldsj.server.entity.GeodataItem;

import java.util.List;

/**
 * @author Jiarui 2020/8/26
 * @author zjh 2020/10/12
 * @update zyq 2020/11/4
 */
public interface GeodataMapper extends BaseMapper<Geodata, String> {

    // 数据目录功能：根据type字段返回数据
    List<Geodata> selectByType1(String type1);

    List<Geodata> selectByType2(String type2);

    // 数据目录功能：根据type字段返回唯一不同值与对应数量
    // 查询出唯一值
    List<Geodata> getDistinctField(String field);

    // 查询该值数据量
    String getCountOfField(String field, String field2);

    // 查询下载数量最多的5条数据
    List<Geodata> getPopularData();

    // 访问次数+1
    void viewTimesPlus(String id);

}
