package edu.zju.gis.dldsj.server.controller;

import edu.zju.gis.dldsj.server.base.BaseController;
import edu.zju.gis.dldsj.server.base.BaseFilter;
import edu.zju.gis.dldsj.server.common.Page;
import edu.zju.gis.dldsj.server.common.Result;
import edu.zju.gis.dldsj.server.entity.Log;
import edu.zju.gis.dldsj.server.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

/**
 * @author zlzhang
 * @date 2020/10/18
 */
@Slf4j
@Controller
@RequestMapping("/log")
public class LogController extends BaseController<Log, LogService, String, BaseFilter<String>> {

    @Autowired
    private LogService logService;

    /***
     * 查询日志（通过ACTID，非主键）
     * @param actId String
     * @return result
     */
    @RequestMapping(value = "/selectbyactid/{actId}", method = RequestMethod.GET)
    @ResponseBody
    public Result<Log> selectLogByName(@PathVariable String actId) {
        return logService.selectByActid(actId);
    }

    /***
     * 查询日志（通过STARTTIME ENDTIME,非主键）
     * @param startTime Timestamp
     * @param endTime Timestamp
     * @return result
     */
    @RequestMapping(value = "/selectbytime", method = RequestMethod.GET)
    @ResponseBody
    public Result selectLogByTime(@RequestParam Timestamp startTime, @RequestParam Timestamp endTime, Page page) {
        return logService.selectByTime(startTime, endTime, page);
    }


}
