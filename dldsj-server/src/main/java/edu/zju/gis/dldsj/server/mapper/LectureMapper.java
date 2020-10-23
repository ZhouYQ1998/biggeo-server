package edu.zju.gis.dldsj.server.mapper;

import edu.zju.gis.dldsj.server.base.BaseMapper;
import edu.zju.gis.dldsj.server.entity.Lecture;

/**
 * @author zyq
 * @date 2020/10/23
 */

public interface LectureMapper extends BaseMapper<Lecture,String> {

    int allDelete();

}
