package edu.zju.gis.dldsj.server.controller;

import edu.zju.gis.dldsj.server.base.BaseController;
import edu.zju.gis.dldsj.server.common.Page;
import edu.zju.gis.dldsj.server.common.Result;
import edu.zju.gis.dldsj.server.entity.Geodata;
import edu.zju.gis.dldsj.server.entity.searchPojo.GeodataSearchPojo;
import edu.zju.gis.dldsj.server.service.GeodataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

/**
 * @author Jiarui
 * @date 2020/8/26
 */

@Controller
@RequestMapping("/geodata")
@Slf4j
public class GeodataController extends BaseController<Geodata, GeodataService,String, GeodataSearchPojo> {


    /**
     * 按照type1 第一级目录来分类
     *
     * @param type
     * @param page
     */
    @RequestMapping(value = "/bytype1", method = RequestMethod.GET)
    @ResponseBody
    public Result getByType1(@RequestParam String type, Page page) {
        return Result.success().setBody(service.selectByType1(type, page));
    }

    /**
     * 按照type2 第二级目录来分类
     *
     * @param type
     * @param page
     */
    @RequestMapping(value = "/bytype2", method = RequestMethod.GET)
    @ResponseBody
    public Result getByType2(@RequestParam String type, Page page) {
        return Result.success().setBody(service.selectByType2(type, page));
    }

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
     * 根据输入的id，更新数据库中下载次数的字段
     * @param id
     */
    @RequestMapping(value = "/downloadplus", method = RequestMethod.GET)
    @ResponseBody
    public Result updateDownload(@RequestParam String id) {
        service.downloadTimesPlus(id);
        return Result.success().setBody(id+"update success");
    }

    /**
     * 返回下载数量最多的五条数据
     */
    @RequestMapping(value = "/populardata", method = RequestMethod.GET)
    @ResponseBody
    public Result getPopularData() {
        return Result.success().setBody(service.getPopularData());
    }


}
