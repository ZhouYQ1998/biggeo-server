package edu.zju.gis.dldsj.server.mapper.mysql;

import edu.zju.gis.dldsj.server.base.BaseMapper;
import edu.zju.gis.dldsj.server.entity.Lecture;

import java.util.Date;

/**
 * @author zyq
 * @date 2020/10/23
 */

public interface LectureMapper extends BaseMapper<Lecture,String> {

    int deleteBeforeTime(Date date);

    Lecture selectByName(String name);

}
