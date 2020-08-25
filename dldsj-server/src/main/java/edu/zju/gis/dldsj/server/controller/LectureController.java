package edu.zju.gis.dldsj.server.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.zju.gis.dldsj.server.base.BaseController;
import edu.zju.gis.dldsj.server.common.Page;
import edu.zju.gis.dldsj.server.common.Result;
import edu.zju.gis.dldsj.server.constant.CodeConstants;
import edu.zju.gis.dldsj.server.entity.Example;
import edu.zju.gis.dldsj.server.entity.Lecture;
import edu.zju.gis.dldsj.server.entity.User;
import edu.zju.gis.dldsj.server.entity.searchPojo.ExampleSearchPojo;
import edu.zju.gis.dldsj.server.entity.searchPojo.LectureSearchPojo;
import edu.zju.gis.dldsj.server.service.ExampleService;
import edu.zju.gis.dldsj.server.service.LectureService;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author Jiarui
 * @date 2020/8/20
 */

@Log4j
@Controller
@RequestMapping("/lecture")
public class LectureController extends BaseController<Lecture, LectureService, String, LectureSearchPojo> {

    @Autowired
    private LectureService lectureService;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public Result getTest() {
        String test = "success";
        return Result.success().setBody(test);
    }

    /**
     * 根据名称
     * @param Lename
     * @return
     */

    @RequestMapping(value = "/byname",method = RequestMethod.GET)
    @ResponseBody
    public Result selectByName(@RequestParam String Lename){
//        return lectureService.selectByName(requestBody).toString();


        Result<Lecture> result = new Result<>();
        Lecture lecture = new Lecture();
        try {
            Lecture body = lectureService.selectByPrimaryKey(Lename);
//            Lecture body = lectureService.selectByName(Lename);
            lecture = body;
            System.out.println(body);
            result.setCode(CodeConstants.SUCCESS).setMessage("获取用户信息成功。").setBody(body);
        }
        catch ( RuntimeException e) {
            result.setCode(CodeConstants.SERVICE_ERROR).setMessage("获取用户信息失败：" + e.getMessage());
        }
        return Result.success();
    }
//
//    /**
//     * @param pageNum 从前端读取的页数
//     * @return
//     */
//
//    @RequestMapping(value = "/getall",method = RequestMethod.GET)
//    @ResponseBody
//    public List<Lecture> selectAll(@RequestParam Integer pageNum){
//        //(pagenum,pagesize)
//        PageHelper.startPage(pageNum, 10);
//        List<Lecture> listL = lectureService.selectAll();
//        PageInfo<Lecture> page = new PageInfo<>(listL);
//        for (Lecture item:listL){
//            System.out.println(item.getName());
//        }
//        page.getPages();
//        System.out.println(page.getPageNum());
//        System.out.println(page.getPages());
//        return listL;
//    }
//

    /**
     * 排序函数
     * @param order 排序字段
     * @param orderType 排序方式
     * @param pageNo
     * @param pageSize
     */

    @RequestMapping(value = "/byorder",method = RequestMethod.GET)
    @ResponseBody
    public Result selectByOrder(@RequestParam String order, @RequestParam String orderType, @RequestParam String pageNo,@RequestParam String pageSize){
        Page page = new Page();
        PageHelper.startPage(page.getPageNo(),page.getPageSize());
        //如果输入desc就降序排列，否则升序排列
        String orderType2;
        //System.out.println(orderType);
        if (orderType.equals("desc")){
            orderType2 = "DESC";
        }
        else{
            orderType2 = "ASC";
        }
        System.out.println(orderType2);
        List<Lecture> listL = lectureService.selectByOrder(order,orderType2);
        PageInfo<Lecture> Repage = new PageInfo<>(listL);
        return Result.success().setBody(listL);
    }
}
