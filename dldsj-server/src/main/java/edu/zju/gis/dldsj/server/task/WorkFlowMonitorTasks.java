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
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Slf4j
@Getter
@Setter
@Component
public class WorkFlowMonitorTasks implements Runnable {
    private CommonSetting setting;
    private WorkFlowService workFlowService;
    private String runId;
    private Date executionDate;

    @Override
    public void run() {
        log.info("开启新的工作流监控任务：dagId=`{}`", runId);
        int i = 0;
        Long dateTimestamp = executionDate.getTime()/1000+8*3600;
        try {
            WorkFlowRun workFlowRun = this.workFlowService.getRunById(runId);
            System.out.println(workFlowRun.getRunId());
            System.out.println(workFlowRun.getStatus());
            while (workFlowRun.getStatus().equals("running")) {
                List<WorkFlowJob> jobs = this.workFlowService.getJobsByRun(runId);
                for (WorkFlowJob job : jobs) {
                    //executionDate此时是Date类型的数据，将其转换成时间戳，并转换时区
                    AirflowTaskInstance instance = this.workFlowService.airflow_getInstance(runId, job.getJobId(), dateTimestamp);
                    if (instance != null) {
                        job.setStartTime(instance.getStartDate());
                        job.setEndTime(instance.getEndDate());
                        job.setStatus(instance.getState());
                        workFlowService.updateJob(job);
                    }
                }
                AirflowDagRun run = this.workFlowService.airflow_getDagRun(runId,dateTimestamp);
                //List<AirflowDagRun> listRun = this.workFlowService.airflow_getRunningDag(runId);
                //AirflowDagRun run = listRun.get(listRun.size()-1);

                System.out.println(runId);
                System.out.println(dateTimestamp);

                workFlowRun.setStatus(run.getState());
                Thread.sleep(setting.getMonitorInterval());

                i++;
                log.info("循环次数"+i);
            }
            workFlowService.updateRun(workFlowRun);
            log.info("工作流`{}`@`{}`监控已完成，最终状态为：{}", runId, workFlowRun.getEndTime(), workFlowRun.getStatus());

        } catch (Exception e) {
            log.error("工作流`" + runId + "`监控失败", e);
        }
    }
}
