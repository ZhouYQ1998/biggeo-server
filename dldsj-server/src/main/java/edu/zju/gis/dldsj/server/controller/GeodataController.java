package edu.zju.gis.dldsj.server.controller;

import edu.zju.gis.dldsj.server.base.BaseController;
import edu.zju.gis.dldsj.server.common.Page;
import edu.zju.gis.dldsj.server.common.Result;
import edu.zju.gis.dldsj.server.entity.Geodata;
import edu.zju.gis.dldsj.server.entity.searchPojo.GeodataSearchPojo;
import edu.zju.gis.dldsj.server.service.GeodataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
     * 按照type来分类
     * @param type
     * @param page
     */
    @RequestMapping(value = "/bytype",method = RequestMethod.GET)
    @ResponseBody
    public Result getByType(@RequestParam String type,Page page){
        return Result.success().setBody(service.selectByType(type,page));
    }

    /**
     * 按照source来分类
     * @param source
     * @param page
     */
    @RequestMapping(value = "/bysource",method = RequestMethod.GET)
    @ResponseBody
    public Result getBySource(@RequestParam String source, Page page){
        return Result.success().setBody(service.selectBySource(source,page));
    }

    /**
     * 按照uploader来分类
     * @param uploader
     * @param page
     */
    @RequestMapping(value = "/byuploader",method = RequestMethod.GET)
    @ResponseBody
    public Result getByUploader(@RequestParam String uploader, Page page){
        return Result.success().setBody(service.selectByUploader(uploader,page));
    }


    /**
     * distinct 根据输入字段名称，返回结果的唯一不同值与对应数量
     * @param field
     */
    @RequestMapping(value = "/dis",method = RequestMethod.GET)
    @ResponseBody
    public Result getdis(@RequestParam String field, Page page){
        Map<String,String> res = service.getDistinctField(field);
        return Result.success().setBody(res);
    }


}
