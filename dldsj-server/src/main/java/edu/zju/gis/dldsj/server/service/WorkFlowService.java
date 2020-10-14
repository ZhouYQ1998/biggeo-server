package edu.zju.gis.dldsj.server.service;


import edu.zju.gis.dldsj.server.common.Page;
import edu.zju.gis.dldsj.server.entity.workflow.*;

import java.util.List;

public interface WorkFlowService {
    int insertDag(WorkFlowDag dag);

    int insertRun(WorkFlowRun run);

    int insertJob(WorkFlowJob job);

    int deleteDagById(String dagId);

    int deleteRunById(String runId);

    int updateDag(WorkFlowDag dag);

    int updateRun(WorkFlowRun run);

    int updateJob(WorkFlowJob job);

    WorkFlowDag getDagById(String id);

    WorkFlowRun getRunById(String id);

    WorkFlowJob getJobById(String id);

    Page<WorkFlowDag> getUserDags(String userId, Page page);

    Page<WorkFlowRun> getUserRuns(String userId, Page page);

    List<WorkFlowJob> getJobsByRun(String runId);

    boolean ifDagExist(String dagId);

    //op in airflow default db
    int airflow_insertDag(AirflowDag dag);

    List<AirflowDagRun> airflow_getRunningDag(String dagId);

    AirflowDagRun airflow_getDagRun(String dagId, long executionDate);

    AirflowTaskInstance airflow_getInstance(String dagId, String jobId, long executionDate);

}
