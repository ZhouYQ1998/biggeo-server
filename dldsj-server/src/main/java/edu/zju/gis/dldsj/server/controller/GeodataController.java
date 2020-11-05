package edu.zju.gis.dldsj.server.controller;

import edu.zju.gis.dldsj.server.base.BaseController;
import edu.zju.gis.dldsj.server.base.BaseFilter;
import edu.zju.gis.dldsj.server.common.Page;
import edu.zju.gis.dldsj.server.common.Result;
import edu.zju.gis.dldsj.server.entity.Geodata;
import edu.zju.gis.dldsj.server.entity.GeodataItem;
import edu.zju.gis.dldsj.server.service.GeodataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Jiarui 2020/8/26
 * @author zjh 2020/10/12
 * @update zyq 2020/11/4
 */

@Controller
@CrossOrigin
@RequestMapping("/geodata")
@Slf4j
public class GeodataController extends BaseController<Geodata, GeodataService, String, BaseFilter<String>> {

    @Autowired
    private GeodataService geodataService;

    @Value("${settings.hdFsUri}")
    private String hdfsUri;

    @Value("${settings.lFsUri}")
    private String lfsUri;

    /**
     * 数据目录功能：按照type1 第一级目录分类
     *
     * @param type String
     * @param page Page
     */
    @RequestMapping(value = "/bytype1", method = RequestMethod.GET)
    @ResponseBody
    public Result<Page<Geodata>> getByType1(@RequestParam String type, Page<Geodata> page) {
        return geodataService.selectByType1(type, page);
    }

    /**
     * 数据目录功能：按照type2 第二级目录分类
     *
     * @param type String
     * @param page Page
     */
    @RequestMapping(value = "/bytype2", method = RequestMethod.GET)
    @ResponseBody
    public Result<Page<Geodata>> getByType2(@RequestParam String type, Page<Geodata> page) {
        return geodataService.selectByType2(type, page);
    }

    /**
     * 数据目录功能：根据type字段返回唯一不同值与对应数量
     *
     * @param field String
     * @param page Page
     */
    @RequestMapping(value = "/dis", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String, String>> getdis(@RequestParam String field, Page<Geodata> page) {
        return geodataService.getDistinctField(field, page);
    }

    /**
     * 查询下载数量最多的5条数据
     */
    @RequestMapping(value = "/populardata", method = RequestMethod.GET)
    @ResponseBody
    public Result<List<Geodata>> getPopularData() {
        return geodataService.getPopularData();
    }

    /**
     * 查询数据集详情
     *
     * @param id String
     */
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Result<List<GeodataItem>> getDetail(@PathVariable String id){
        return geodataService.getDatail(id);
    }

}
