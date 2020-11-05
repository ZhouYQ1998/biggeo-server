package edu.zju.gis.dldsj.server.mapper;

import edu.zju.gis.dldsj.server.base.BaseMapper;
import edu.zju.gis.dldsj.server.entity.GeodataItem;

import java.util.List;

/**
 * @author zyq 2020/11/4
 */
public interface GeodataItemMapper extends BaseMapper<GeodataItem, String> {

    // 查询数据集详情
    List<GeodataItem> getDatail(String dataset);

}
