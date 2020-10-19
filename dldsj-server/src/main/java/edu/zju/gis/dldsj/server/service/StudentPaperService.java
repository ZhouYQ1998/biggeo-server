package edu.zju.gis.dldsj.server.service;

import edu.zju.gis.dldsj.server.base.BaseService;
import edu.zju.gis.dldsj.server.common.Result;
import edu.zju.gis.dldsj.server.entity.AcademicPaper;
import edu.zju.gis.dldsj.server.entity.StudentPaper;
import edu.zju.gis.dldsj.server.entity.User;

import java.util.List;
import java.util.Map;

/**
 * @author zyq 2020/10/16
 */
public interface StudentPaperService extends BaseService<StudentPaper, String> {

    Result<List<StudentPaper>> selectNew();

}
