package edu.zju.gis.dldsj.server.service;

import edu.zju.gis.dldsj.server.base.BaseService;
import edu.zju.gis.dldsj.server.entity.Literature;
import edu.zju.gis.dldsj.server.entity.searchPojo.LiteratureSearchPojo;

import java.util.List;
import java.util.Map;

/**
 * @author Jiarui
 * @date 2020/8/27
 */
public interface LiteratureService extends BaseService<Literature,String> {

    //根据输入字段名称，返回结果的唯一不同值与对应数量
    Map<String,String> getDistinctField(LiteratureSearchPojo param);

    //计算查询结果中出现次数最多的作者
    List<String> getSumOfField(LiteratureSearchPojo param, String field);
}
