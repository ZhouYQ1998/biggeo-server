package edu.zju.gis.dldsj.server.controller;

import com.github.pagehelper.PageHelper;
import edu.zju.gis.dldsj.server.base.BaseController;
import edu.zju.gis.dldsj.server.base.BaseFilter;
import edu.zju.gis.dldsj.server.common.Page;
import edu.zju.gis.dldsj.server.common.Result;
import edu.zju.gis.dldsj.server.entity.Lecture;
import edu.zju.gis.dldsj.server.service.LectureService;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * @author Jiarui
 * @date 2020/8/20
 */

@Log4j
@Controller
@RequestMapping("/lecture")
public class LectureController extends BaseController<Lecture, LectureService, String, BaseFilter<String>> {

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

        PageHelper.startPage(page.getPageNo(),page.getPageSize());
        return Result.success().setBody(service.selectByOrder(order,orderType,page));
    }
}
