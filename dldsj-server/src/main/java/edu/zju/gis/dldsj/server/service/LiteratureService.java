package edu.zju.gis.dldsj.server.service;

import edu.zju.gis.dldsj.server.base.BaseService;
import edu.zju.gis.dldsj.server.common.Page;
import edu.zju.gis.dldsj.server.entity.Literature;

import java.util.Map;

/**
 * @author Jiarui
 * @date 2020/8/27
 */
public interface LiteratureService extends BaseService<Literature,String> {

    Page<Literature> selectBySource(String source,Page page);

    //根据输入字段名称，返回结果的唯一不同值与对应数量
    Map<String,String> getDistinctField(String field);
}
