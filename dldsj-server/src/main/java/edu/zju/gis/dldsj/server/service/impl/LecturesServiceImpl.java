package edu.zju.gis.dldsj.server.service.impl;

import edu.zju.gis.dldsj.server.base.BaseServiceImpl;
import edu.zju.gis.dldsj.server.entity.Lecture;
import edu.zju.gis.dldsj.server.mapper.mysql.LectureMapper;
import edu.zju.gis.dldsj.server.service.LectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author zyq
 * @date 2020/10/23
 */

@Service
public class LecturesServiceImpl extends BaseServiceImpl<LectureMapper, Lecture,String> implements LectureService {

    @Autowired
    private LectureMapper lectureMapper;

    @Override
    public int deleteBeforeTime(Date date){
        return lectureMapper.deleteBeforeTime(date);
    }

    @Override
    public Lecture selectByName(String name){
        return lectureMapper.selectByName(name);
    };

}
