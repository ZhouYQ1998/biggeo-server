package edu.zju.gis.dldsj.server.mapper;

import edu.zju.gis.dldsj.server.base.BaseMapper;
import edu.zju.gis.dldsj.server.entity.AcademicPaper;
import edu.zju.gis.dldsj.server.entity.Lecture;
import edu.zju.gis.dldsj.server.entity.StudentPaper;

import java.util.List;

/**
 * @author zyq 2020/10/16
 */
public interface AcademicPaperMapper extends BaseMapper<AcademicPaper, String> {

    List<AcademicPaper> selectNew();

}