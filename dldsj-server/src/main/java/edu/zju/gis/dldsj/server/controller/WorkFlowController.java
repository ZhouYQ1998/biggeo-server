package edu.zju.gis.dldsj.server.controller;

import edu.zju.gis.dldsj.server.common.Page;
import edu.zju.gis.dldsj.server.common.Result;
import edu.zju.gis.dldsj.server.config.CommonSetting;
import edu.zju.gis.dldsj.server.entity.workflow.WorkFlowDag;
import edu.zju.gis.dldsj.server.entity.workflow.WorkFlowJob;
import edu.zju.gis.dldsj.server.entity.workflow.WorkFlowParam;
import edu.zju.gis.dldsj.server.entity.workflow.WorkFlowRun;
import edu.zju.gis.dldsj.server.service.ParallelModelService;
import edu.zju.gis.dldsj.server.service.WorkFlowService;
import edu.zju.gis.dldsj.server.task.MoniterTasks;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author Jiarui
 * @date 2020/9/24
 */


@Slf4j
@CrossOrigin
@Controller
@RequestMapping("/workflow")
public class WorkFlowController {
    @Autowired
    private CommonSetting setting;
//   @Autowired
//    private ParallelModelService parallelModelService;
    @Autowired
    private WorkFlowService workFlowService;
    //@Autowired
    //private MonitorTasks monitorTasks;


    /**
     * 已保存工作流模版列表
     * @param userId
     * @param page
     * @return
     */
    @RequestMapping(value = "/dags/all", method = RequestMethod.GET)
    @ResponseBody
    public Result getDags(@SessionAttribute("userId") String userId, Page page) {
        Page<WorkFlowDag> dags = null;
        dags = this.workFlowService.getUserDags(userId, page);
        if (dags != null)
            return Result.success().setBody(dags);
        else
            return Result.error();
    }


    /**
     * 单工作流模版详情
     * @param dagId
     * @return
     */
    @RequestMapping(value = "/dags/{dagId}", method = RequestMethod.GET)
    @ResponseBody
    public Result getDagInfo(@PathVariable String dagId) {
        WorkFlowDag dag = this.workFlowService.getDagById(dagId);
        if (dag != null)
            return Result.success().setBody(dag);
        else
            return Result.error();
    }


    /**
     * 保存工作流模版
     * @param userId
     * @param workFlowParam
     * @return
     */
    @RequestMapping(value = "/dags/save", method = RequestMethod.POST)
    @ResponseBody
    public Result saveDag(@SessionAttribute("userId") String userId, @RequestBody WorkFlowParam workFlowParam) {
        WorkFlowDag dag = new WorkFlowDag();
        dag.setId(workFlowParam.getId());
        dag.setUserId(userId);
        dag.setConnections(workFlowParam.getConnectionsAsJson());
        dag.setNodes(workFlowParam.getNodesAsJson());
        dag.setDescription(workFlowParam.getDescription());
        dag.setStyle(workFlowParam.getStyle());
        dag.setLastModifyTime(new Date());
        if (this.workFlowService.ifDagExist(dag.getId())) {
            this.workFlowService.updateDag(dag);
            return Result.success().setMessage(this.workFlowService.getDagById(dag.getId()).getName() + "工作流更新成功。");
        } else {
            dag.setName(workFlowParam.getName());
            dag.setCreatedTime(dag.getLastModifyTime());
            this.workFlowService.insertDag(dag);
            return Result.success().setMessage(dag.getName() + "工作流保存成功。");
        }
    }

    /**
     * 删除工作流模版
     * @param dagId
     * @return
     */
    @RequestMapping(value = "/dags/delete/{dagId}", method = RequestMethod.DELETE)
    @ResponseBody
    public Result deleteDag(@PathVariable String dagId) {
        int numb = this.workFlowService.deleteDagById(dagId);
        if (numb > 0)
            return Result.success().setMessage("工作流删除成功.");
        else
            return Result.error().setMessage("工作流删除失败.");
    }


    /**
     *获取工作流任务列表
     * @param userId
     * @param page
     * @return
     */
    @RequestMapping(value = "/tasks/all", method = RequestMethod.GET)
    @ResponseBody
    public Result getTasks(@SessionAttribute("userId") String userId, Page page) {
        Page<WorkFlowRun> tasks = null;
        tasks = this.workFlowService.getUserRuns(userId, page);
        if (tasks != null)
            return Result.success().setBody(tasks);
        else
            return Result.error();
    }

    //获取工作流任务信息
    @RequestMapping(value = "/tasks/{taskId}", method = RequestMethod.GET)
    @ResponseBody
    public Result getRunInfo(@PathVariable String taskId) {
        WorkFlowRun task = this.workFlowService.getRunById(taskId);
        if (task != null)
            return Result.success().setBody(task);
        else
            return Result.error();
    }

    /**
     * 删除工作流任务
     * @param taskId
     * @return
     */
    //TODO 工作流任务删除需要与airFlow原生任务记录联动
    @RequestMapping(value = "/tasks/delete/{taskId}", method = RequestMethod.DELETE)
    @ResponseBody
    public Result deleteTask(@PathVariable String taskId) {
        int numb = this.workFlowService.deleteRunById(taskId);
        if (numb > 0)
            return Result.success().setMessage("任务删除成功.");
        else
            return Result.error().setMessage("任务删除失败.");
    }

    /**
     * 工作流任务子任务列表
     * @param taskId
     * @return
     */
    @RequestMapping(value = "/tasks/{taskId}/jobs", method = RequestMethod.GET)
    @ResponseBody
    public Result getJobs(@PathVariable String taskId) {
        List<WorkFlowJob> tasks = null;
        tasks = this.workFlowService.getJobsByRun(taskId);
        if (tasks != null)
            return Result.success().setBody(tasks);
        else
            return Result.error();
    }

    /**
     * 子任务详情
     * @param jobId
     * @return
     */
    @RequestMapping(value = "/jobs/{jobId}", method = RequestMethod.GET)
    @ResponseBody
    public Result getJobInfo(@PathVariable String jobId) {
        WorkFlowJob job = this.workFlowService.getJobById(jobId);
        if (job != null)
            return Result.success().setBody(job);
        else
            return Result.error();
    }



}
