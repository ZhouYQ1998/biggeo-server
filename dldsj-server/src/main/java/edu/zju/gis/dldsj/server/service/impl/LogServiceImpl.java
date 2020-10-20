package edu.zju.gis.dldsj.server.service.impl;

import com.github.pagehelper.PageHelper;
import edu.zju.gis.dldsj.server.base.BaseServiceImpl;
import edu.zju.gis.dldsj.server.common.Page;
import edu.zju.gis.dldsj.server.common.Result;
import edu.zju.gis.dldsj.server.constant.CodeConstants;
import edu.zju.gis.dldsj.server.entity.Log;
import edu.zju.gis.dldsj.server.mapper.LogMapper;
import edu.zju.gis.dldsj.server.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

/**
 * @author zlzhang
 * @date 2020/10/16
 * @update zyq 2020/10/20
 */
@Service
public class LogServiceImpl extends BaseServiceImpl<LogMapper, Log, String> implements LogService {

    @Autowired
    private LogMapper logMapper;

    public int autoDelete(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,-7);
        Timestamp time = new Timestamp(calendar.getTimeInMillis());
        return logMapper.deleteBeforeTime(time);
    }

    @Override
    public Result<Log> selectByActid(String actId) {
        Result<Log> result = new Result<>();
        try {
            Log log = logMapper.selectByActid(actId);
            if(log != null){
                result.setCode(CodeConstants.SUCCESS).setBody(log).setMessage("查询成功");
            }
            else{
                result.setCode(CodeConstants.VALIDATE_ERROR).setMessage("查询失败：无结果");
            }
        } catch (RuntimeException e) {
            result.setCode(CodeConstants.SERVICE_ERROR).setMessage("查询失败：" + e.getMessage());
        }
        return result;
    }

    @Override
    public Result<Page<Log>> selectByTime(Timestamp startTime, Timestamp endTime,Page<Log> page) {
        Result<Page<Log>> result = new Result<>();
        try {
            List<Log> log = logMapper.selectByTime(startTime, endTime);
            PageHelper.startPage(page.getPageNo(), page.getPageSize());
            if(log != null){
                Page<Log> logPage = new Page<>(logMapper.selectByTime(startTime, endTime));
                result.setCode(CodeConstants.SUCCESS).setBody(logPage).setMessage("查询成功");
            }
            else{
                result.setCode(CodeConstants.VALIDATE_ERROR).setMessage("查询失败：无结果");
            }
        } catch (RuntimeException e) {
            result.setCode(CodeConstants.SERVICE_ERROR).setMessage("查询失败：" + e.getMessage());
        }
        return result;
    }


}
