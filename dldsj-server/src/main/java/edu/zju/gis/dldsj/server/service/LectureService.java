package edu.zju.gis.dldsj.server.service;

import edu.zju.gis.dldsj.server.base.BaseService;
import edu.zju.gis.dldsj.server.entity.Lecture;

import java.util.Date;

/**
 * @author zyq
 * @date 2020/10/23
 */

public interface LectureService extends BaseService<Lecture,String> {

    int deleteBeforeTime(Date date);

    Lecture selectByName(String name);

}
