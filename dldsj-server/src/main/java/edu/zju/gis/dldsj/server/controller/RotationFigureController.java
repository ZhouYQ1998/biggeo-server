package edu.zju.gis.dldsj.server.controller;

import edu.zju.gis.dldsj.server.common.Page;
import edu.zju.gis.dldsj.server.common.Result;
import edu.zju.gis.dldsj.server.service.RotationFigureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;


/**
 * @author Jinghaoyu
 * @version 1.0 2020/08/20
 *
 * 轮播图Controller
 */

@Slf4j
@CrossOrigin
@Controller
@RequestMapping("/rotationFigure")
public class RotationFigureController {

    @Resource
    private RotationFigureService rotationFigureService;

    @RequestMapping("/list")
    @ResponseBody
    public Result list(){
        return Result.success().setBody(rotationFigureService.findAll());
    }

    //分页查询
    @RequestMapping("/listByPage")
    @ResponseBody
    public Result findByPaging(Page page){
        return Result.success().setBody(rotationFigureService.getByPage(page.getOffset(), page.getPageSize()));
    }
}
