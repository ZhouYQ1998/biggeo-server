package edu.zju.gis.dldsj.server.mapper;

import edu.zju.gis.dldsj.server.base.BaseMapper;
import edu.zju.gis.dldsj.server.entity.Geodata;

import java.util.List;

/**
 * @author Jiarui
 * @date 2020/8/26
 * @author: zjh
 * @date: 20201012
 */
public interface GeodataMapper extends BaseMapper<Geodata, String> {

    //数据目录功能，根据type、uploader、source或useName等目录字段，返回数据(单选)
    List<Geodata> selectByType1(String type1);

    List<Geodata> selectByType2(String type2);

    List<Geodata> selectByUploader(String uploader);

    List<Geodata> selectBySource(String source);

    List<Geodata> selectByUserName(String userName);


    //根据输入字段名称，返回结果的唯一不同值与对应数量
    //查询出有哪些不重复的字段
    List<Geodata> getDistinctField(String field);

    //计算该字段共有多少条数据
    String getCountOfField(String field, String field2);

    //下载次数+1
    void downloadTimesPlus(String id);

    //下载量最多的5条数据
    List<Geodata> getPopularData();

}
