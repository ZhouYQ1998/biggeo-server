package edu.zju.gis.dldsj.server.service;

import edu.zju.gis.dldsj.server.base.BaseService;
import edu.zju.gis.dldsj.server.entity.Geodata;

import java.util.List;
import java.util.Map;

/**
 * @author Jiarui
 * @date 2020/8/26
 */
public interface GeodataService extends BaseService<Geodata,String> {

    //数据目录功能，根据type、uploader或source等目录字段，返回数据(单选)
    List<Geodata> selectByType(String type);
    List<Geodata> selectByUploader(String uploader);
    List<Geodata> selectBySource(String source);

    //根据输入字段名称，返回结果的唯一不同值与对应数量
    Map<String,String> getDistinctField(String field);


}
