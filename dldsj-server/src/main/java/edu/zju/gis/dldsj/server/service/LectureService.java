package edu.zju.gis.dldsj.server.service;

import edu.zju.gis.dldsj.server.base.BaseMapper;
import edu.zju.gis.dldsj.server.base.BaseService;
import edu.zju.gis.dldsj.server.entity.Lecture;

import java.util.Date;
import java.util.List;

/**
 * @author Jiarui
 * @date 2020/8/19 10:04 上午
 */

public interface LectureService extends BaseService<Lecture,String> {

    Lecture selectByPrimaryKey(String id);

    List<Lecture> selectAll();

    List<Lecture> selectByOrder(String type,String typeOrder);

}
