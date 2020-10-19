package edu.zju.gis.dldsj.server.service.impl;

import edu.zju.gis.dldsj.server.base.BaseServiceImpl;
import edu.zju.gis.dldsj.server.common.Result;
import edu.zju.gis.dldsj.server.constant.CodeConstants;
import edu.zju.gis.dldsj.server.entity.AcademicPaper;
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

    public Result<List<StudentPaper>> selectNew(){
        Result<List<StudentPaper>> result = new Result<>();
        try {
            List<StudentPaper> newStudentPaper = studentPaperMapper.selectNew();
            result.setCode(CodeConstants.SUCCESS).setBody(newStudentPaper).setMessage("查询成功");
        } catch (RuntimeException e) {
            result.setCode(CodeConstants.SERVICE_ERROR).setMessage("查询失败：" + e.getMessage());
        }
        return result;
    };

}
