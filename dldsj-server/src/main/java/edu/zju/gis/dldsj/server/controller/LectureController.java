package edu.zju.gis.dldsj.server.controller;

import com.github.pagehelper.PageHelper;
import edu.zju.gis.dldsj.server.base.BaseController;
import edu.zju.gis.dldsj.server.base.BaseFilter;
import edu.zju.gis.dldsj.server.common.Page;
import edu.zju.gis.dldsj.server.common.Result;
import edu.zju.gis.dldsj.server.entity.Lecture;
import edu.zju.gis.dldsj.server.entity.User;
import edu.zju.gis.dldsj.server.service.LectureService;
import edu.zju.gis.dldsj.server.service.UserService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author Jiarui
 * @date 2020/8/20
 */

@Log4j
@Controller
@CrossOrigin
@RequestMapping("/lecture")
public class LectureController extends BaseController<Lecture, LectureService, String, BaseFilter<String>> {

    @Autowired
    private LectureService lectureService;

    /***
     * 查询最新讲座
     * @return result
     */
    @RequestMapping(value = "/selectnew", method = RequestMethod.GET)
    @ResponseBody
    public Result<List<Lecture>> selectNew() {
        return lectureService.selectNew();
    }

}
