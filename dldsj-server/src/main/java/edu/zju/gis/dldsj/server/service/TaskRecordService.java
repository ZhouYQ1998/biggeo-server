package edu.zju.gis.dldsj.server.service;

import edu.zju.gis.dldsj.server.common.Page;
import edu.zju.gis.dldsj.server.entity.Monitor;
import edu.zju.gis.dldsj.server.entity.TaskRecord;

import java.util.List;

/**
 * @author Keran Sun (katus)
 * @version 1.0, 2020-10-05
 */
public interface TaskRecordService {
    TaskRecord select(String id);

    int update(TaskRecord record);

    int insert(TaskRecord record);

    List<TaskRecord> getByUser(String user);

    List<TaskRecord> getByUserWithState(String user, Monitor.FinalStatus status);

    Page<TaskRecord> getAll(Page<TaskRecord> page);

    Page<TaskRecord> getPagesByUser(String user, Page<TaskRecord> page);

    Page<TaskRecord> getPagesByUserWithState(String user, Page<TaskRecord> page, Monitor.FinalStatus status);
}
