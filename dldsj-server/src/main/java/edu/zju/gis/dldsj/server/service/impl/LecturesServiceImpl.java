package edu.zju.gis.dldsj.server.service.impl;

import com.github.pagehelper.PageHelper;
import edu.zju.gis.dldsj.server.base.BaseServiceImpl;
import edu.zju.gis.dldsj.server.common.Page;
import edu.zju.gis.dldsj.server.entity.Lecture;
import edu.zju.gis.dldsj.server.mapper.LectureMapper;
import edu.zju.gis.dldsj.server.service.LectureService;
import org.apache.hadoop.yarn.webapp.hamlet.Hamlet;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jiarui
 * @date 2020/8/24 5:59 下午
 */

@Service
public class LecturesServiceImpl extends BaseServiceImpl<LectureMapper, Lecture,String> implements LectureService {

    public Page<Lecture> selectByOrder(String type, String orderType, Page page) {

        //如果输入desc就降序排列，否则升序排列
        String orderType2;
        if (orderType.equals("desc")){
            orderType2 = "DESC";
        }
        else{
            orderType2 = "ASC";
        }
        PageHelper.startPage(page.getPageNo(),page.getPageSize());
        return new Page<>(mapper.selectByOrder(type,orderType2));
    }


}
