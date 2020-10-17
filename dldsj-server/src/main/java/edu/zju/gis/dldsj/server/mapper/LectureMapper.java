package edu.zju.gis.dldsj.server.mapper;
import edu.zju.gis.dldsj.server.base.BaseMapper;
import edu.zju.gis.dldsj.server.common.Page;
import edu.zju.gis.dldsj.server.common.Result;
import edu.zju.gis.dldsj.server.entity.Lecture;
import edu.zju.gis.dldsj.server.entity.User;

import java.util.List;


/**
 * @author Jiarui
 * @date 2020/8/19 9:52 上午
 */

public interface LectureMapper extends BaseMapper<Lecture,String> {

    List<Lecture> selectNew();

}
