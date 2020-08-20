package edu.zju.gis.dldsj.server.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.zju.gis.dldsj.server.entity.RotationFigure;
import edu.zju.gis.dldsj.server.service.RotationFigureService;
import lombok.extern.slf4j.Slf4j;
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

    //设计Map集合存储需要给页面的对象数据
    private Map<String, Object> result = new HashMap<String, Object>();

    //分页查询
    @RequestMapping("/listByPage")
    @ResponseBody  //将对象转成json
    private Map<String,Object> listByPage(Integer page, Integer rows){
        //设置分页参数
        PageHelper.startPage(page, rows);

        //查询所有数据
        List<RotationFigure> list = rotationFigureService.findAll();

        //使用PageInfo来封装查询结果
        PageInfo<RotationFigure> pageInfo = new PageInfo<RotationFigure>(list);

        //从PageInfo对象取出查询结果
        //总记录数
        long total = pageInfo.getTotal();
        //当前页数据列表
        List<RotationFigure> custList = pageInfo.getList();

        result.put("total", total);
        result.put("rows", custList);
        return result;
    }

    //保存数据
    @RequestMapping("/save")
    @ResponseBody
    public Map<String,Object> save(RotationFigure rotationFigure){
        try {
            rotationFigureService.save(rotationFigure);
            result.put("success",true);
        } catch (Exception e){
            e.printStackTrace();
            result.put("success",false);
            result.put("msg",e.getMessage());
        }
        return result;
    }

    //根据id查询对象
    @RequestMapping("/findById")
    @ResponseBody
    public RotationFigure findById(Integer id){
        return rotationFigureService.findById(id);
    }

    //删除数据
    @RequestMapping("/delete")
    @ResponseBody
    public Map<String, Object> delete(Integer[] id){
        try {
            rotationFigureService.delete(id);
            result.put("success", true);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            result.put("success", false);
            result.put("msg", e.getMessage());
        }
        return result;
    }
}
