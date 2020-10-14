package edu.zju.gis.dldsj.server.controller;

import edu.zju.gis.dldsj.server.base.BaseController;
import edu.zju.gis.dldsj.server.base.BaseFilter;
import edu.zju.gis.dldsj.server.common.Result;
import edu.zju.gis.dldsj.server.entity.teachModel;
import edu.zju.gis.dldsj.server.service.teachModelService;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author Jiarui
 * @date 2020/10/13
 */

@Log4j
@Controller
@RequestMapping("teachModel")
public class teachModelController extends BaseController<teachModel, teachModelService,String, BaseFilter<String>> {


    /**
     * 管理员上传教学案例
     * @param role
     * @param teachModel
     * @return
     */
    @RequestMapping(value = "/uploadTeachModel",method = RequestMethod.POST)
    @ResponseBody
    public Result uploadTeachModel(@SessionAttribute("role") String role,teachModel teachModel){
        if (role.equals("visitor")){
            return Result.error().setMessage("游客无此权限");
        }
        if  (role.equals("manager")){
            return Result.success().setBody(service.insert(teachModel));
        }
        else return Result.error();
    }

    /**
     * 管理员删除teachModel
     * @param role
     * @param teachModelId
     * @return
     */
    @RequestMapping(value = "/unregisterTeachModel/{teachModelId}",method = RequestMethod.DELETE)
    @ResponseBody
    public Result unregisterTeachModel(@SessionAttribute("role") String role,@PathVariable String teachModelId){
        if (role.equals("visitor")){
            return Result.error().setMessage("游客无此权限");
        }
        if  (role.equals("manager")){
            return Result.success().setBody(service.delete(teachModelId));
        }
        else return Result.error();
    }

}
