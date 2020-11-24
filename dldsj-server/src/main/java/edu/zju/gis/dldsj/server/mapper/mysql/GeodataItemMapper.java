package edu.zju.gis.dldsj.server.mapper.mysql;

import edu.zju.gis.dldsj.server.base.BaseMapper;
import edu.zju.gis.dldsj.server.entity.GeodataItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zyq 2020/11/4
 */
public interface GeodataItemMapper extends BaseMapper<GeodataItem, String> {

    // 查询数据集详情
    List<GeodataItem> getDatail(String dataset);

    // 根据数据集ID和文件名获取公共数据信息
    List<GeodataItem> getItemBySetAndTitle(@Param("dataset") String dataset, @Param("title") String title);

}
