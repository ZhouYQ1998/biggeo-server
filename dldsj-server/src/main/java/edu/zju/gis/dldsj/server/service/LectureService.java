package edu.zju.gis.dldsj.server.service;

import edu.zju.gis.dldsj.server.base.BaseService;
import edu.zju.gis.dldsj.server.entity.Lecture;

/**
 * @author zyq
 * @date 2020/10/23
 */

public interface LectureService extends BaseService<Lecture,String> {

    int allDelete();

}
