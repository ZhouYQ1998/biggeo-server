package edu.zju.gis.dldsj.server.mapper;

import edu.zju.gis.dldsj.server.base.BaseMapper;
import edu.zju.gis.dldsj.server.entity.Geodata;

import java.util.List;

/**
 * @author Jiarui
 * @date 2020/8/26
 */
public interface GeodataMapper extends BaseMapper<Geodata,String> {

    //数据目录功能，根据type、uploader或source等目录字段，返回数据(单选)

    List<Geodata> selectByType(String type);
    List<Geodata> selectByUploader(String uploader);
    List<Geodata> selectBySource(String source);


    //根据输入字段名称，返回结果的唯一不同值与对应数量
    //查询出有哪些不重复的字段
    List<Geodata> getDistinctField(String field);
    //计算该字段共有多少条数据
    String getCountOfField(String field,String field2);


}
