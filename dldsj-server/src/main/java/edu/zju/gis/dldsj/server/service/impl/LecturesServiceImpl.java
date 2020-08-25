package edu.zju.gis.dldsj.server.service.impl;

import edu.zju.gis.dldsj.server.base.BaseFilter;
import edu.zju.gis.dldsj.server.base.BaseServiceImpl;
import edu.zju.gis.dldsj.server.entity.Lecture;
import edu.zju.gis.dldsj.server.mapper.LectureMapper;
import edu.zju.gis.dldsj.server.service.LectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jiarui
 * @date 2020/8/24 5:59 下午
 */

@Service
public class LecturesServiceImpl extends BaseServiceImpl<LectureMapper, Lecture,String> implements LectureService {
    @Autowired
    LectureMapper lectureMapper;

    @Override
    public Lecture selectByPrimaryKey(String id) {
        return lectureMapper.selectByPrimaryKey(id);
    }

    public List<Lecture> search(BaseFilter<String> params) {
        return lectureMapper.search(params);
    }

    public int insertSelective(Lecture lecture) {
        return 0;
    }

    public int updateByPrimaryKeySelective(Lecture lecture) {
        return 0;
    }

    public int updateByPrimaryKey(Lecture lecture) {
        return 0;
    }

    public int deleteByPrimaryKey(String pk) {
        return 0;
    }

    public List<Lecture> selectByPage(int offset, int size) {
        return null;
    }

    public int deleteBatch(List<String> ts) {
        return 0;
    }

    @Override
    public List<Lecture> selectAll() {
        return lectureMapper.selectAll();
    }

    @Override
    public List<Lecture> selectByOrder(String type, String typeOrder) {
        return lectureMapper.selectByOrder(type,typeOrder);
    }


}
