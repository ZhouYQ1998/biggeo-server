package edu.zju.gis.dldsj.server.controller;

import edu.zju.gis.dldsj.server.base.BaseController;
import edu.zju.gis.dldsj.server.base.BaseFilter;
import edu.zju.gis.dldsj.server.common.Result;
import edu.zju.gis.dldsj.server.entity.AcademicPaper;
import edu.zju.gis.dldsj.server.entity.Lecture;
import edu.zju.gis.dldsj.server.entity.StudentPaper;
import edu.zju.gis.dldsj.server.service.AcademicPaperService;
import edu.zju.gis.dldsj.server.service.StudentPaperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author zyq 2020/09/23
 */
@Slf4j
@CrossOrigin
@Controller
@RequestMapping("/academicpaper")
public class AcademicPaperController extends BaseController<AcademicPaper, AcademicPaperService, String, BaseFilter<String>> {

    @Autowired
    private AcademicPaperService academicPaperService;

    /***
     * 查询最新论文
     * @return result
     */
    @RequestMapping(value = "/selectnew", method = RequestMethod.GET)
    @ResponseBody
    public Result<List<AcademicPaper>> selectNew() {
        return academicPaperService.selectNew();
    }

}
