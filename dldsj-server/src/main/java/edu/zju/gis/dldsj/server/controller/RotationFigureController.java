package edu.zju.gis.dldsj.server.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.zju.gis.dldsj.server.common.ResultInfo;
import edu.zju.gis.dldsj.server.entity.RotationFigure;
import edu.zju.gis.dldsj.server.mapper.RotationFigureMapper;
import edu.zju.gis.dldsj.server.service.RotationFigureService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import scala.Int;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
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

    //查询所有数据，返回json格式
    @RequestMapping("/list")
    @ResponseBody
    public List<RotationFigure> list(){
        return rotationFigureService.findAll();
    }

    //分页查询
    @RequestMapping("/listByPage")
    @ResponseBody  //将对象转成json
    public ResultInfo findByPaging(Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        Map param = new HashMap();
        Page<RotationFigure> data = rotationFigureService.findByPaging(param);
        JSONObject result = new JSONObject();
        result.put("employees",data);
        result.put("pages",data.getPages());
        result.put("total",data.getTotal());
        return ResultInfo.getInfo(result);
    }
}
