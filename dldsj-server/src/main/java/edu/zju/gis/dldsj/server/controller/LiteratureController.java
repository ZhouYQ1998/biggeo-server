package edu.zju.gis.dldsj.server.controller;


import edu.zju.gis.dldsj.server.base.BaseController;
import edu.zju.gis.dldsj.server.common.Page;
import edu.zju.gis.dldsj.server.common.Result;
import edu.zju.gis.dldsj.server.entity.Literature;
import edu.zju.gis.dldsj.server.entity.searchPojo.LectureSearchPojo;
import edu.zju.gis.dldsj.server.entity.searchPojo.LiteratureSearchPojo;
import edu.zju.gis.dldsj.server.service.LiteratureService;
import lombok.Data;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.Map;

/**
 * @author Jiarui
 * @date 2020/8/20
 */

@Log4j
@Controller
@RequestMapping("/literature")
public class LiteratureController extends BaseController<Literature, LiteratureService,String, LiteratureSearchPojo> {

    /**
     * 按照source来分类
     *
     * @param source
     * @param page
     */
    @RequestMapping(value = "/bysource", method = RequestMethod.GET)
    @ResponseBody
    public Result getBySource(@RequestParam String source, Page page) {
        return Result.success().setBody(service.selectBySource(source, page));
    }

    /**
     * distinct 根据输入字段名称，返回结果的唯一不同值与对应数量
     *
     * @param field
     */
    @RequestMapping(value = "/dis", method = RequestMethod.GET)
    @ResponseBody
    public Result getdis(@RequestParam String field, Page page) {
        Map<String, String> res = service.getDistinctField(field);
        return Result.success().setBody(res);
    }

    /**
     * BY time
     *
     * @param time1
     * @param time2
     * @param page
     */
    @RequestMapping(value = "/time", method = RequestMethod.GET)
    @ResponseBody
    public Result getByTime(@RequestParam String time1, String time2, Page page) {
        return Result.success().setBody(service.selectByTime(time1, time2, page));
    }
}
