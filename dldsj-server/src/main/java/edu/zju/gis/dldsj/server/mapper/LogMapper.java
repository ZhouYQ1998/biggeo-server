package edu.zju.gis.dldsj.server.mapper;

import edu.zju.gis.dldsj.server.base.BaseMapper;
import edu.zju.gis.dldsj.server.entity.Log;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author zlzhang
 * @date 2020/9/30
 * @update zyq 2020/10/20
 */
public interface LogMapper extends BaseMapper<Log, String> {

    int deleteBeforeTime(Timestamp time);

    Log selectByActid (String actId );

    List<Log> selectByTime (Timestamp startTime, Timestamp endTime );

}
