package edu.zju.gis.dldsj.server.service;

import edu.zju.gis.dldsj.server.base.BaseService;
import edu.zju.gis.dldsj.server.common.Page;
import edu.zju.gis.dldsj.server.common.Result;
import edu.zju.gis.dldsj.server.entity.Log;

import java.sql.Timestamp;

/**
 * @author zlzhang
 * @date 2020/10/16
 */
public interface LogService extends BaseService<Log, String> {

    Result<Log> selectByActid(String actId);

    Result<Page<Log>> selectByTime(Timestamp startTime, Timestamp endTime,Page page);
}
