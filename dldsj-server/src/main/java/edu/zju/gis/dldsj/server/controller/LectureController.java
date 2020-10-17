package edu.zju.gis.dldsj.server.controller;

import com.github.pagehelper.PageHelper;
import edu.zju.gis.dldsj.server.base.BaseController;
import edu.zju.gis.dldsj.server.base.BaseFilter;
import edu.zju.gis.dldsj.server.common.Page;
import edu.zju.gis.dldsj.server.common.Result;
import edu.zju.gis.dldsj.server.entity.Lecture;
import edu.zju.gis.dldsj.server.service.LectureService;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * @author Jiarui
 * @date 2020/8/20
 */

@Log4j
@Controller
@CrossOrigin
@RequestMapping("/lecture")
public class LectureController extends BaseController<Lecture, LectureService, String, BaseFilter<String>> {

}
