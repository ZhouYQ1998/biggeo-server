package edu.zju.gis.dldsj.server.service.impl;

import com.github.pagehelper.PageHelper;
import edu.zju.gis.dldsj.server.common.Page;
import edu.zju.gis.dldsj.server.entity.workflow.*;
import edu.zju.gis.dldsj.server.mapper.*;
import edu.zju.gis.dldsj.server.service.WorkFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkFlowServiceImpl implements WorkFlowService {
    @Autowired
    public WorkFlowDagMapper workFlowDagMapper;
    @Autowired
    public WorkFlowRunMapper workFlowRunMapper;
    @Autowired
    public WorkFlowJobMapper workFlowJobMapper;
    @Autowired
    public AirflowDagMapper airflowDagMapper;
    @Autowired
    public AirflowDagRunMapper airflowDagRunMapper;
    @Autowired
    public AirflowTaskInstanceMapper airflowInstanceMapper;

    @Override
    public int insertDag(WorkFlowDag dag) {
        return this.workFlowDagMapper.insert(dag);
    }

    @Override
    public int insertRun(WorkFlowRun run) {
        return this.workFlowRunMapper.insert(run);
    }

    @Override
    public int insertJob(WorkFlowJob job) {
        return this.workFlowJobMapper.insert(job);
    }

    @Override
    public int deleteDagById(String dagId) {
        this.airflowDagRunMapper.deleteByDagId(dagId);
        return this.workFlowDagMapper.deleteByPrimaryKey(dagId);
    }

    @Override
    public int deleteDagByNameAndUserId(String name, String userId) {
        return workFlowDagMapper.deleteByNameAndUserId(name,userId);
    }

    @Override
    public int deleteRunById(String runId) {
        List<WorkFlowJob> jobs = this.workFlowJobMapper.selectByRunId(runId);
        for (WorkFlowJob job : jobs) {
            this.workFlowJobMapper.deleteByPrimaryKey(job.getJobId());
        }
        return this.workFlowRunMapper.deleteByPrimaryKey(runId);
    }

    @Override
    public int updateDag(WorkFlowDag dag) {
        return this.workFlowDagMapper.updateByPrimaryKeySelective(dag);
    }

    @Override
    public int updateRun(WorkFlowRun run) {
        return this.workFlowRunMapper.updateByPrimaryKeySelective(run);
    }

    @Override
    public int updateJob(WorkFlowJob job) {
        return this.workFlowJobMapper.updateByPrimaryKeySelective(job);
    }

    @Override
    public WorkFlowDag getDagById(String id) {
        return this.workFlowDagMapper.selectByPrimaryKey(id);
    }

    @Override
    public WorkFlowDag selectDagByNameAndUserId(String name, String userId) {
        return workFlowDagMapper.selectByNameAndUserId(name,userId);
    }

    @Override
    public WorkFlowRun getRunById(String id) {
        return this.workFlowRunMapper.selectByPrimaryKey(id);
    }

    @Override
    public WorkFlowJob getJobById(String id) {
        return this.workFlowJobMapper.selectByPrimaryKey(id);
    }



    @Override
    public Page<WorkFlowDag> getUserDags(String userId, Page page) {
        PageHelper.startPage(page.getPageNo(), page.getPageSize());
        return new Page<>(this.workFlowDagMapper.selectByUserId(userId));
    }

    @Override
    public Page<WorkFlowRun> getUserRuns(String userId, Page page) {
        PageHelper.startPage(page.getPageNo(), page.getPageSize());
        return new Page<>(this.workFlowRunMapper.selectByUserId(userId));
    }

    @Override
    public List<WorkFlowJob> getJobsByRun(String runId) {
        return this.workFlowJobMapper.selectByRunId(runId);
    }

    @Override
    public Page<WorkFlowDag> searchByDagName(String dagName, Page page) {
        PageHelper.startPage(page.getPageNo(), page.getPageSize());
        return new Page<>(this.workFlowDagMapper.searchByDagName(dagName));
    }

    @Override
    public boolean ifDagExist(String dagId) {
        return this.workFlowDagMapper.selectByPrimaryKey(dagId) != null;
    }

    //op in airflow default db
    @Override
    public int airflow_insertDag(AirflowDag dag) {
        return this.airflowDagMapper.insert(dag);
    }

    @Override
    public List<AirflowDagRun> airflow_getRunningDag(String dagId) {
        return this.airflowDagRunMapper.selectRunningDagById(dagId);
    }

    @Override
    public AirflowDagRun airflow_getDagRun(String dagId, long executionDate) {
        return this.airflowDagRunMapper.selectDagRunByTime(dagId, executionDate);
    }

    @Override
    public AirflowTaskInstance airflow_getInstance(String dagId, String jobId, long executionDate) {
        return this.airflowInstanceMapper.selectInstance(dagId, jobId, executionDate);
    }
}
