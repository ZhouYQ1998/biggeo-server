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
 * @update zyq 2020/10/20
 */
@Slf4j
@Controller
@RequestMapping("/log")
public class LogController extends BaseController<Log, LogService, String, BaseFilter<String>> {

    @Autowired
    private LogService logService;

    /**
     * 插入日志
     * @param log Log
     * @return Result
     */
    @RequestMapping(value = "/insert", method = RequestMethod.PUT)
    @ResponseBody
    @Override
    public Result<Log> insert(@RequestBody Log log) {
        logService.autoDelete();
        return service.insert(log);
    }

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
    public Result<Page<Log>> selectLogByTime(@RequestParam Timestamp startTime, @RequestParam Timestamp endTime, Page<Log> page) {
        return logService.selectByTime(startTime, endTime, page);
    }


}
