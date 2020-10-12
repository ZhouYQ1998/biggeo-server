package edu.zju.gis.dldsj.server.service;

import edu.zju.gis.dldsj.server.base.BaseService;
import edu.zju.gis.dldsj.server.common.Page;
import edu.zju.gis.dldsj.server.entity.Geodata;

import java.util.List;
import java.util.Map;

/**
 * @author Jiarui
 * @date 2020/8/26
 * @author: zjh
 * @date: 20201012
 */
public interface GeodataService extends BaseService<Geodata,String> {

    //数据目录功能，根据type、uploader或source等目录字段，返回数据(单选)
    Page<Geodata> selectByType1(String type, Page page);

    Page<Geodata> selectByType2(String type, Page page);

    Page<Geodata> selectBySource(String source, Page page);

    Page<Geodata> selectByUserName(String userName, Page page);

    //根据输入字段名称，返回结果的唯一不同值与对应数量
    Map<String, String> getDistinctField(String field);

    //下载次数+1
    void downloadTimesPlus(String id);
    //统计下载量最高的5条数据
    List<Geodata> getPopularData();
}
