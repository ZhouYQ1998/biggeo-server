package edu.zju.gis.dldsj.server.service.impl;

import com.github.pagehelper.PageHelper;
import edu.zju.gis.dldsj.server.base.BaseServiceImpl;
import edu.zju.gis.dldsj.server.common.Page;
import edu.zju.gis.dldsj.server.entity.Lecture;
import edu.zju.gis.dldsj.server.mapper.LectureMapper;
import edu.zju.gis.dldsj.server.service.LectureService;
import org.springframework.stereotype.Service;


/**
 * @author Jiarui
 * @date 2020/8/24 5:59 下午
 */

@Service
public class LecturesServiceImpl extends BaseServiceImpl<LectureMapper, Lecture,String> implements LectureService {

}
