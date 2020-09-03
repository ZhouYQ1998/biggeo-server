package edu.zju.gis.dldsj.server.mapper;

import edu.zju.gis.dldsj.server.base.BaseMapper;
import edu.zju.gis.dldsj.server.entity.Literature;
import edu.zju.gis.dldsj.server.entity.searchPojo.LiteratureSearchPojo;
import java.util.List;

/**
 * @author Jiarui
 * @date 2020/8/27
 */

public interface LiteratureMapper extends BaseMapper<Literature,String> {

    //查询出有哪些不重复的字段
    List<Literature> getDistinctField(LiteratureSearchPojo param);

    //计算该字段共有多少条数据
    String getCountOfField(LiteratureSearchPojo param);
}
