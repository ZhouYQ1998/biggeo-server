package edu.zju.gis.dldsj.server.mapper.mysql;

import edu.zju.gis.dldsj.server.entity.TaskRecord;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Keran Sun (katus)
 * @version 1.0, 2020-10-05
 */
public interface TaskRecordMapper {
    @Select("select * from mr_task_record order by SUBMIT_TIME desc")
    List<TaskRecord> selectAll();

    @Select("select * from mr_task_record where CREATE_USER = #{user} order by SUBMIT_TIME desc")
    List<TaskRecord> selectByUser(String user);

    @Select("select * from mr_task_record where CREATE_USER = #{user} and STATE = #{status} order by SUBMIT_TIME desc")
    List<TaskRecord> selectByUserAndStatus(String user, String status);

    TaskRecord selectByPrimaryKey(String pk);

    int updateByPrimaryKeySelective(TaskRecord record);

    int insertSelective(TaskRecord record);
}
