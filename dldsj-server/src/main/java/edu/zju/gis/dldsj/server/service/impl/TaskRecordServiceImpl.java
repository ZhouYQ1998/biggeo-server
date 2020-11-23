package edu.zju.gis.dldsj.server.service.impl;

import com.github.pagehelper.PageHelper;
import edu.zju.gis.dldsj.server.common.Page;
import edu.zju.gis.dldsj.server.entity.Monitor;
import edu.zju.gis.dldsj.server.entity.TaskRecord;
import edu.zju.gis.dldsj.server.mapper.mysql.TaskRecordMapper;
import edu.zju.gis.dldsj.server.service.TaskRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Keran Sun (katus)
 * @version 1.0, 2020-10-05
 */
@Service
public class TaskRecordServiceImpl implements TaskRecordService {
    @Autowired
    private TaskRecordMapper taskRecordMapper;

    @Override
    public List<TaskRecord> getByUser(String user) {
        return taskRecordMapper.selectByUser(user);
    }

    @Override
    public List<TaskRecord> getByUserWithState(String user, Monitor.FinalStatus status) {
        return taskRecordMapper.selectByUserAndStatus(user, status.name());
    }

    @Override
    public Page<TaskRecord> getAll(Page<TaskRecord> page) {
        PageHelper.startPage(page.getPageNo(), page.getPageSize());
        List<TaskRecord> result = taskRecordMapper.selectAll();
        return new Page<>(result);
    }

    @Override
    public Page<TaskRecord> getPagesByUser(String user, Page<TaskRecord> page) {
        PageHelper.startPage(page.getPageNo(), page.getPageSize());
        List<TaskRecord> result = taskRecordMapper.selectByUser(user);
        return new Page<>(result);
    }

    @Override
    public Page<TaskRecord> getPagesByUserWithState(String user, Page<TaskRecord> page, Monitor.FinalStatus status) {
        PageHelper.startPage(page.getPageNo(), page.getPageSize());
        List<TaskRecord> result = taskRecordMapper.selectByUserAndStatus(user, status.name());
        return new Page<>(result);
    }

    @Override
    public TaskRecord select(String id) {
        return taskRecordMapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(TaskRecord record) {
        return taskRecordMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int insert(TaskRecord record) {
        return taskRecordMapper.insertSelective(record);
    }
}
