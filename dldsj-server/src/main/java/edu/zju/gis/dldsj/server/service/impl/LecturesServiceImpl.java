package edu.zju.gis.dldsj.server.service.impl;

import com.github.pagehelper.PageHelper;
import edu.zju.gis.dldsj.server.base.BaseServiceImpl;
import edu.zju.gis.dldsj.server.common.Page;
import edu.zju.gis.dldsj.server.common.Result;
import edu.zju.gis.dldsj.server.constant.CodeConstants;
import edu.zju.gis.dldsj.server.entity.Lecture;
import edu.zju.gis.dldsj.server.entity.User;
import edu.zju.gis.dldsj.server.mapper.LectureMapper;
import edu.zju.gis.dldsj.server.mapper.UserMapper;
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
    private LectureMapper lectureMapper;

    public Result<List<Lecture>> selectNew(){
        Result<List<Lecture>> result = new Result<>();
        try {
            List<Lecture> newLectures = lectureMapper.selectNew();
            result.setCode(CodeConstants.SUCCESS).setBody(newLectures).setMessage("查询成功");
        } catch (RuntimeException e) {
            result.setCode(CodeConstants.SERVICE_ERROR).setMessage("查询失败：" + e.getMessage());
        }
        return result;
    };

}
