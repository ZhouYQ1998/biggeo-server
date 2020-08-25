package edu.zju.gis.dldsj.server.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.zju.gis.dldsj.server.base.BaseController;
import edu.zju.gis.dldsj.server.common.Page;
import edu.zju.gis.dldsj.server.common.Result;
import edu.zju.gis.dldsj.server.entity.Lecture;
import edu.zju.gis.dldsj.server.entity.searchPojo.LectureSearchPojo;
import edu.zju.gis.dldsj.server.service.LectureService;
import lombok.extern.log4j.Log4j;
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

    /**
     * 排序函数
     * @param order 排序字段
     * @param orderType 排序方式
     * @param pageNo
     * @param pageSize
     */

    @RequestMapping(value = "/byorder",method = RequestMethod.GET)
    @ResponseBody
    public Result selectByOrder(@RequestParam String order, @RequestParam String orderType, @RequestParam Integer pageNo,@RequestParam Integer pageSize){
        Page page = new Page();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        //如果输入desc就降序排列，否则升序排列
        String orderType2;
        if (orderType.equals("desc")){
            orderType2 = "DESC";
        }
        else{
            orderType2 = "ASC";
        }

        String orderBy = order+" "+orderType2;
        PageHelper.startPage(page.getPageNo(),page.getPageSize(),orderBy);
//        Page<Lecture> ll = service.search(param,page);
        List<Lecture> listL = service.selectByOrder(order,orderType);
        Page<Lecture> Repage = new Page<>(listL);
        return Result.success().setBody(Repage);
    }
}
