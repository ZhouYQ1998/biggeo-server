package edu.zju.gis.dldsj.server.controller;

import edu.zju.gis.dldsj.server.base.BaseController;
import edu.zju.gis.dldsj.server.base.BaseFilter;
import edu.zju.gis.dldsj.server.entity.AcademicPaper;
import edu.zju.gis.dldsj.server.entity.StudentPaper;
import edu.zju.gis.dldsj.server.service.AcademicPaperService;
import edu.zju.gis.dldsj.server.service.StudentPaperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zyq 2020/09/23
 */
@Slf4j
@Controller
@RequestMapping("/academicpaper")
public class AcademicPaperController extends BaseController<AcademicPaper, AcademicPaperService, String, BaseFilter<String>> {

    @Autowired
    private AcademicPaperService academicPaperService;

}
