package edu.zju.gis.dldsj.server.service.impl;

import edu.zju.gis.dldsj.server.base.BaseServiceImpl;
import edu.zju.gis.dldsj.server.common.Result;
import edu.zju.gis.dldsj.server.constant.CodeConstants;
import edu.zju.gis.dldsj.server.entity.StudentPaper;
import edu.zju.gis.dldsj.server.entity.User;
import edu.zju.gis.dldsj.server.mapper.StudentPaperMapper;
import edu.zju.gis.dldsj.server.mapper.UserMapper;
import edu.zju.gis.dldsj.server.service.StudentPaperService;
import edu.zju.gis.dldsj.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author zyq 2020/09/23
 */
@Service
public class StudentPaperServiceImpl extends BaseServiceImpl<StudentPaperMapper, StudentPaper, String> implements StudentPaperService {

    @Autowired
    private StudentPaperMapper studentPaperMapper;

}
