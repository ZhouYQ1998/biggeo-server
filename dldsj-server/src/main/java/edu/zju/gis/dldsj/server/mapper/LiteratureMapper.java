package edu.zju.gis.dldsj.server.mapper;

import edu.zju.gis.dldsj.server.base.BaseMapper;
import edu.zju.gis.dldsj.server.entity.Literature;


import java.util.List;

/**
 * @author Jiarui
 * @date 2020/8/27
 */

public interface LiteratureMapper extends BaseMapper<Literature,String> {

    //数据目录，按照文献的分类属性进行快捷查询
    List<Literature> selectBySource(String source);

    //根据输入字段名称，返回结果的唯一不同值与对应数量
    //查询出有哪些不重复的字段
    List<Literature> getDistinctField(String field);
    //计算该字段共有多少条数据
    String getCountOfField(String field,String field2);
}
