package edu.zju.gis.dldsj.server.task;

/**
 * @author Jiarui
 * @date 2020/9/24
 */

import edu.zju.gis.dldsj.server.config.CommonSetting;
import edu.zju.gis.dldsj.server.entity.workflow.AirflowDagRun;
import edu.zju.gis.dldsj.server.entity.workflow.AirflowTaskInstance;
import edu.zju.gis.dldsj.server.entity.workflow.WorkFlowJob;
import edu.zju.gis.dldsj.server.entity.workflow.WorkFlowRun;
import edu.zju.gis.dldsj.server.service.WorkFlowService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;

@Slf4j
@Getter
@Setter
public class WorkFlowMonitorTasks implements Runnable {
    private CommonSetting setting;
    private WorkFlowService workFlowService;
    private String runId;
    private Date executionDate;

    @Override
    public void run() {
        log.info("开启新的工作流监控任务：dagId=`{}`", runId);
        try {
            WorkFlowRun workFlowRun = this.workFlowService.getRunById(runId);
            while (workFlowRun.getStatus().equals("running")) {
                List<WorkFlowJob> jobs = this.workFlowService.getJobsByRun(runId);
                for (WorkFlowJob job : jobs) {
                    AirflowTaskInstance instance = this.workFlowService.airflow_getInstance(runId, job.getJobid(), executionDate);
                    if (instance != null) {
                        job.setStarttime(instance.getStartDate());
                        job.setEndtime(instance.getEndDate());
                        job.setStatus(instance.getState());
                        workFlowService.updateJob(job);
                    }
                }
                AirflowDagRun run = this.workFlowService.airflow_getDagRun(runId, executionDate);
                workFlowRun.setStatus(run.getState());
                Thread.sleep(setting.getMonitorInterval());
            }
            workFlowService.updateRun(workFlowRun);
            log.info("工作流`{}`@`{}`监控已完成，最终状态为：{}", runId, workFlowRun.getEndtime(), workFlowRun.getStatus());

        } catch (Exception e) {
            log.error("工作流`" + runId + "`监控失败", e);
        }
    }
}
