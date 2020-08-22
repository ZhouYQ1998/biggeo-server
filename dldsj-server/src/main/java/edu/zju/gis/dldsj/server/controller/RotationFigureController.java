package edu.zju.gis.dldsj.server.controller;

import com.github.pagehelper.PageHelper;
import edu.zju.gis.dldsj.server.common.Result;
import edu.zju.gis.dldsj.server.service.RotationFigureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


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
    public Result findByPaging(Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        Map param = new HashMap();
        return Result.success().setBody(rotationFigureService.findByPaging(param));
    }
}
