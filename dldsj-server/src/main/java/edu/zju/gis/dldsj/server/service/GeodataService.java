package edu.zju.gis.dldsj.server.service;

import edu.zju.gis.dldsj.server.base.BaseService;
import edu.zju.gis.dldsj.server.common.Page;
import edu.zju.gis.dldsj.server.common.Result;
import edu.zju.gis.dldsj.server.entity.Geodata;
import edu.zju.gis.dldsj.server.entity.GeodataItem;

import java.util.List;
import java.util.Map;

/**
 * @author Jiarui 2020/8/26
 * @author zjh 2020/10/12
 * @update zyq 2020/11/4
 */
public interface GeodataService extends BaseService<Geodata, String> {

    // 数据目录功能：根据type字段返回数据
    Result<Page<Geodata>> selectByType1(String type, Page<Geodata> page);

    Result<Page<Geodata>> selectByType2(String type, Page<Geodata> page);

    // 数据目录功能：根据type字段返回唯一不同值与对应数量
    Result<Map<String, String>> getDistinctField(String field, Page<Geodata> page);

    // 查询下载数量最多的5条数据
    Result<List<Geodata>> getPopularData();

    // 查询数据集详情
    Result<List<GeodataItem>> getDatail(String id);

}
