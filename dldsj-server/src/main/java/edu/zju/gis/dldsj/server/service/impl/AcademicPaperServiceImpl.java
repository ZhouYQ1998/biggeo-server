package edu.zju.gis.dldsj.server.service.impl;

import edu.zju.gis.dldsj.server.base.BaseServiceImpl;
import edu.zju.gis.dldsj.server.entity.AcademicPaper;
import edu.zju.gis.dldsj.server.mapper.mysql.AcademicPaperMapper;
import edu.zju.gis.dldsj.server.service.AcademicPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zyq 2020/09/23
 */
@Service
public class AcademicPaperServiceImpl extends BaseServiceImpl<AcademicPaperMapper, AcademicPaper, String> implements AcademicPaperService {

    @Autowired
    private AcademicPaperMapper academicPaperMapper;

}
