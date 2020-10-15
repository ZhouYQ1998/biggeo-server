package edu.zju.gis.dldsj.server.controller;

import edu.zju.gis.dldsj.server.common.Page;
import edu.zju.gis.dldsj.server.common.Result;
import edu.zju.gis.dldsj.server.config.CommonSetting;
import edu.zju.gis.dldsj.server.entity.ParallelModelWithBLOBs;
import edu.zju.gis.dldsj.server.entity.workflow.*;
import edu.zju.gis.dldsj.server.service.ParallelModelService;
import edu.zju.gis.dldsj.server.service.WorkFlowService;
import edu.zju.gis.dldsj.server.task.WorkFlowMonitorTasks;
import edu.zju.gis.dldsj.server.task.MonitorTasks;
import edu.zju.gis.dldsj.server.utils.SSHUtil;
import edu.zju.gis.dldsj.server.utils.fs.FsManipulator;
import edu.zju.gis.dldsj.server.utils.fs.FsManipulatorFactory;
import edu.zju.gis.dldsj.server.utils.fs.LocalFsManipulator;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    @Autowired
    private ParallelModelService parallelModelService;
    @Autowired
    private WorkFlowService workFlowService;
    @Autowired
    private MonitorTasks monitorTasks;


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
        Page<WorkFlowRun> tasks = this.workFlowService.getUserRuns(userId, page);
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
        List<WorkFlowJob> tasks = this.workFlowService.getJobsByRun(taskId);
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



    //提交工作流任务
    @RequestMapping(value = "/tasks/submit", method = RequestMethod.POST)
    @ResponseBody
    public Result submitDag(@SessionAttribute("userId") String userId, @RequestBody WorkFlowParam workFlowParam) throws IOException, InterruptedException {
        //遍历模型，整理参数，如拼接in，out参数的用户空间根目录，判断es索引名
        workFlowParam.setId(UUID.randomUUID().toString());
        for (NodeInfo node : workFlowParam.getNodes()) {
            ParallelModelWithBLOBs model = parallelModelService.select(node.getModelId());

            System.out.println(node.getModelId());
            System.out.println(node.getTaskName());
            System.out.println(model.getMainClass());

            JSONArray modelParams = new JSONArray(model.getParameters());
            for (int i = 0; i < node.getParams().size(); i++) {
                JSONObject obj = modelParams.getJSONObject(i);
                if (obj.has("out") || obj.has("in")) {
                    String path = node.getParams().get(i).getValue();
                    if (path.startsWith("ES:"))
                        node.getParams().get(i).setValue(path.replace("ES:", ""));
                    else
                        node.getParams().get(i).setValue(Paths.get(setting.getUserSpaceRootPath(), userId, path).toString());
                }
                if (obj.has("tag")) {
                    String val = node.getParams().get(i).getValue();
                    String tag = obj.getString("tag");
                    node.getParams().get(i).setValue(tag + " " + val);
                }
            }
        }
        //TODO 根据workFlowParam构建airflow所需python文件 <单独设置各节点运行参数>
        List<String> sparkSetting = new ArrayList<>();
        String driverMemory = "4g";
        String numExecutors = "4";
        String executorMemory = "4g";
        String executorCores = "4";
        sparkSetting.add(driverMemory);
        sparkSetting.add(numExecutors);
        sparkSetting.add(executorMemory);
        sparkSetting.add(executorCores);
        List<JobNodeConfig> jobNodeConfigs = new ArrayList<>();
        List<NodeInfo> nodes = workFlowParam.getNodes();
        for (int nodeindex = 0; nodeindex < nodes.size(); nodeindex++) {
            NodeInfo node = nodes.get(nodeindex);

            System.out.println(nodes.size()+node.getTaskName());

            JobNodeConfig config = new JobNodeConfig();
            config.setTaskId(node.getId());
            config.setModelId(node.getModelId());
            config.setDependsOnPast(true);
            config.setArtifactId(node.getModelId());
//            config.setRetries();
//            config.setRetryDelay();
//            config.setMaxRetryDelay();
//            config.setExecutionTimeout();
//            config.setEnv();
            config.setParams(node.getParamValueList());
            config.setSparkSetting(sparkSetting);
            jobNodeConfigs.add(config);
        }
        Path filePath = Paths.get(setting.getAirFlowDagsPath(), workFlowParam.getId() + ".py");
        System.out.println(workFlowParam.getId());
        System.out.println("the file has been written");

        if (!BuildDag(filePath, userId, workFlowParam, jobNodeConfigs))
            return Result.error("工作流调度文件生产失败.");
        //向airFlow数据库插入Dag记录，加速任务调度
        AirflowDag airflowDag = new AirflowDag();
        airflowDag.setDagId(workFlowParam.getId());
        airflowDag.setIsPaused(false);//default true
        airflowDag.setIsSubdag(false);
        airflowDag.setIsActive(true);
        airflowDag.setLastSchedulerRun(new Date());
        airflowDag.setFileloc(filePath.toString());
        airflowDag.setOwners(userId);

        System.out.println("inserting airflow");

        this.workFlowService.airflow_insertDag(airflowDag);
        //提交手动激活工作流shell命令
//        //在本机上airflow安装在conda环境中
//        String pythonEnv = "/Users/jiarui/opt/anaconda3/bin/activate && conda activate /Users/jiarui/opt/anaconda3/envs/py38";
//        String pythonEnv2 = "conda activate py38";
//        String startCmd = String.format("export AIRFLOW_HOME=%s;airflow trigger_dag", setting.getAirFlowHome(),airflowDag.getDagId());
////        SSHHelper.runSSH(setting.getNameNode(), setting.getUsername(), setting.getPassword(), startCmd, setting.getParallelFilePath());
//        System.out.println(startCmd);
//        //CmdUtil.runLocal(pythonEnv2);
//        int r = CmdUtil.runLocal(startCmd);
//        System.out.println(r);
//        //CmdUtil.runLocal("touch /Users/jiarui/oo2.txt");
//        System.out.println("ssh started");

        //提交手动激活工作流shell命令
        String startCmd = String.format("export AIRFLOW_HOME=%s;airflow unpause %s;airflow trigger_dag %s", setting.getAirFlowHome(), airflowDag.getDagId(), airflowDag.getDagId());
        SSHUtil.runSSH(setting.getNameNode(), setting.getUsername(), setting.getPassword(), startCmd, setting.getParallelFilePath());

        //监控airflow.dagRun的记录数，确定工作流已启动
        for (int loop = setting.getJobFailInterval() / setting.getMonitorInterval(); loop >= 0; loop--) {
            log.info("工作流提交验证倒数：" + loop);
            List<AirflowDagRun> airflowDagRuns = this.workFlowService.airflow_getRunningDag(airflowDag.getDagId());
            if (airflowDagRuns.size() > 0) {
                //更新workflow_run&_job表，记录
                AirflowDagRun airflowDagRun = airflowDagRuns.get(0);
                WorkFlowRun runInstance = new WorkFlowRun();
                runInstance.setRunId(airflowDag.getDagId());
                runInstance.setName(workFlowParam.getName());
                runInstance.setUserId(userId);
                runInstance.setStatus("running");
                runInstance.setStartTime(airflowDagRun.getExecutionDate());
                runInstance.setEndTime(runInstance.getStartTime());
                this.workFlowService.insertRun(runInstance);
                for (int i = 0; i < workFlowParam.getNodes().size(); i++) {
                    NodeInfo node = workFlowParam.getNodes().get(i);
                    WorkFlowJob job = new WorkFlowJob();
                    job.setRunId(UUID.randomUUID().toString());
                    job.setRunId(runInstance.getRunId());
                    job.setJobId(node.getId());
                    job.setName(node.getTaskName());

                    System.out.println(job.getName());

                    job.setModel(node.getModelId());
                    job.setParams(node.getParamAsJson());
                    job.setStatus("running");
                    this.workFlowService.insertJob(job);
                }
                //启动任务监控线程，持续更新jobs表
                WorkFlowMonitorTasks monitor = new WorkFlowMonitorTasks();
                monitor.setSetting(setting);
                monitor.setRunId(runInstance.getRunId());
                monitor.setExecutionDate(runInstance.getStartTime());
                monitor.setWorkFlowService(workFlowService);
                monitorTasks.execute(monitor);
                return Result.success().setMessage("工作流提交成功。");
            }
            Thread.sleep(setting.getMonitorInterval());
        }
        return Result.error("工作流提交失败.");
    }


    /**
     * test
     * @return
     */
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public Result test() throws ParseException, IOException {
        String runId = "eb0db2cf-84dd-4bcf-8ecb-ea4aaf1244ff";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        Date date = dateFormat.parse("2020-10-08 11:17:38");
        List<AirflowDagRun> list = workFlowService.airflow_getRunningDag(runId);
        //AirflowDagRun run = workFlowService.airflow_getDagRun(runId,date);
        AirflowDagRun run = list.get(0);
        if (run == null){
            System.out.println("niu");
            return Result.success().setBody("null");
        }
        else {
            System.out.println(run.getId());
            System.out.println(run.getDagId());
            System.out.println(run.getExecutionDate());
            Date date1 = run.getExecutionDate();
            Long createTime = date1.getTime();
            System.out.println(createTime/1000+8*3600);
            System.out.println(date1);
            return Result.success().setBody(run);
        }

//        //提交手动激活工作流shell命令
//        String dagId = "example_bash_operator";
//        String startCmd = String.format("export AIRFLOW_HOME=%s;airflow unpause %s;airflow trigger_dag %s", setting.getAirFlowHome(), dagId, dagId);
//        SSHUtil.runSSH(setting.getNameNode(), setting.getUsername(), setting.getPassword(), startCmd, setting.getParallelFilePath());
 //       return Result.success();
    }


    private boolean BuildDag(Path filePath, String userId, WorkFlowParam workFlowParam, List<JobNodeConfig> nodeConfigs) {
        List<String> content = new ArrayList<>();
        try {
            content.add(buildHead(userId, true, 1, workFlowParam.getId()));
            nodeConfigs.forEach(node -> content.add(node.buildTask(parallelModelService)));
            for (int i = 0; i < workFlowParam.getConnections().size(); i++) {
                content.add(workFlowParam.getConnections().get(i).getSource().replace("-","_") + ">>" + workFlowParam.getConnections().get(i).getTarget().replace("-","_"));
            }
            FsManipulator fsManipulator = FsManipulatorFactory.create();
            ((LocalFsManipulator) fsManipulator).writeLines(filePath, content);
            return true;
        } catch (IOException e) {
            log.error("文件`" + filePath + "写入异常", e);
            return false;
        }
    }

    private static String buildHead(String user, boolean dependsOnPast, int retries, String dagId) {
        return String.format("# -*- encoding: UTF-8 -*-\n" +
                "from airflow import DAG\n" +
                "from airflow.operators.bash_operator import BashOperator\n" +
                "from datetime import datetime\n" +
                "from datetime import timedelta\n\n" +
                "default_args = {\n" +
                "    'owner': '%s',\n" +
                "    'depends_on_past': %s,\n" +
                "    'start_date': datetime(2018,8,23),\n" +
                "    'retries': %d,\n" +
                "    'retry_delay': timedelta(minutes=1)\n" +
                "}\n\n" +
                "dag = DAG(\n" +
                "    dag_id='%s',\n" +
                "    schedule_interval=None,\n" +
                "    default_args=default_args\n" +
                ")\n\n", user, dependsOnPast ? "True" : "False", retries, dagId);
    }




}
