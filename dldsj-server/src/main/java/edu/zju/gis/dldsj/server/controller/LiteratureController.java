package edu.zju.gis.dldsj.server.controller;


import edu.zju.gis.dldsj.server.base.BaseController;
import edu.zju.gis.dldsj.server.common.Page;
import edu.zju.gis.dldsj.server.common.Result;
import edu.zju.gis.dldsj.server.entity.Literature;
import edu.zju.gis.dldsj.server.entity.searchPojo.LiteratureSearchPojo;
import edu.zju.gis.dldsj.server.service.LiteratureService;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
     * distinct 根据输入字段名称，返回结果的唯一不同值与对应数量
     * @param param
     */
    @RequestMapping(value = "/dis", method = RequestMethod.POST)
    @ResponseBody
    public Result getdis(@RequestBody LiteratureSearchPojo param) {
        Map<String, String> res = service.getDistinctField(param);
        return Result.success().setBody(res);
    }

    /**
     * distinct 根据输入字段名称，返回结果的唯一不同值与对应数量
     * @param param
     */
    @RequestMapping(value = "/name", method = RequestMethod.POST)
    @ResponseBody
    public Result getname(@RequestBody LiteratureSearchPojo param,String field) {
        Map<String,Object> res = service.getSumOfField(param,field);
        return Result.success().setBody(res);
    }

}
