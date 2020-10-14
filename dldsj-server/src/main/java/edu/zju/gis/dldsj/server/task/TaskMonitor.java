package edu.zju.gis.dldsj.server.task;

import edu.zju.gis.dldsj.server.config.CommonSetting;
import edu.zju.gis.dldsj.server.entity.Monitor;
import edu.zju.gis.dldsj.server.entity.ParallelModelWithBLOBs;
import edu.zju.gis.dldsj.server.entity.TaskRecord;
import edu.zju.gis.dldsj.server.service.MonitorService;
import edu.zju.gis.dldsj.server.service.TaskRecordService;
import edu.zju.gis.dldsj.server.service.UserService;
import edu.zju.gis.dldsj.server.utils.HttpRequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TaskMonitor implements Runnable {
    private final CommonSetting setting;
    private final String userId;
    private final TaskRecordService taskRecordService;
    private final MonitorService monitorService;
    private final UserService userService;
//    private UserDataService userDataService;
//    private UserDataFieldService userDataFieldService;
    private final TaskRecord record;
    private final JSONArray params;
    private final JSONArray spatialOut;
    private final ParallelModelWithBLOBs model;
    private final boolean addToMyData;

    public TaskMonitor(CommonSetting setting, String userId, TaskRecordService taskRecordService, MonitorService monitorService
            , UserService userService/*, UserDataService userDataService, UserDataFieldService userDataFieldService*/
            , TaskRecord record, ParallelModelWithBLOBs model, JSONArray params, JSONArray spatialOut, boolean addToMyData) {
        this.setting = setting;
        this.userId = userId;
        this.taskRecordService = taskRecordService;
        this.monitorService = monitorService;
        this.userService = userService;
//        this.userDataService = userDataService;
//        this.userDataFieldService = userDataFieldService;
        this.record = record;
        this.model = model;
        this.params = params;
        this.spatialOut = spatialOut;
        this.addToMyData = addToMyData;
    }

    @Override
    public void run() {
        log.info(String.format("new thread running with jobName=`%s` and user=`%s`", record.getApplicationId(), record.getCreateUser()));
        String jobId = null;
        try {
            /*
             * 第一部分: 根据jobName寻找对应的job 初步插入监控表
             * 找到更新后进入第二部分; 如果没有找到则监控失败, 中止监控线程.
             */
            Monitor monitor = new Monitor();
            monitor.setId(record.getApplicationId());
            monitor.setName(record.getApplicationId());
            monitor.setUser(record.getCreateUser());
            monitor.setState(Monitor.FinalStatus.UNDEFINED.name());
            monitorService.insert(monitor);
            // 第一个while循环用于获取jobId
            // 解析出的数据即为详细的app信息,循环遍历获取jobName的jobId
            log.info("查询" + record.getApplicationId() + "的任务id...");
            String monitorResult;
            for (int loop = setting.getJobFailInterval() / setting.getMonitorInterval(); loop >= 0; loop--) {
                monitorResult = HttpRequestUtil.get(setting.getJobMonitor(), null, "utf-8");
                // 当app的个数为1时，monitorResult好像不是一个JsonObject
                // 解析获得的数据
                // 解析第一层 apps
                JSONObject apps = new JSONObject(monitorResult);
                if (!apps.has("apps") || apps.get("apps").toString().equals("null")) {
                    log.info("集群环境中的第一个app，暂时的获得返回值为空");
                    Thread.sleep(setting.getMonitorInterval());
                    continue;
                }
                // 解析第三层既真正的信息层
                JSONArray infos = apps.getJSONObject("apps").getJSONArray("app");
                int i;
                for (i = infos.length() - 1; i >= 0; i--) {
                    if (infos.getJSONObject(i).getString("name").equals(record.getApplicationId())) {
                        jobId = infos.getJSONObject(i).getString("id");
                        log.info("Success in finding jobId = " + jobId);
                        monitor = Monitor.fromJSONObject(infos.getJSONObject(i));
                        // 在这里将数据库里的以jobName临时设置的id改为实际的jobId
                        monitorService.updateByName(monitor);
                        break;
                    }
                }
                //如果已经找到了对应的任务，则退出这个while循环
                if (i >= 0)
                    break;
                Thread.sleep(setting.getMonitorInterval());
            }
            if (jobId == null || jobId.isEmpty()) {
                monitor.setState(Monitor.State.FAILED.name());
                monitor.setFinalStatus(Monitor.FinalStatus.FAILED.name());
                monitorService.updateByName(monitor);
                log.info("任务" + record.getApplicationId() + "提交失败");
                return;
            }
            /*
             * 第二部分: 根据jobId反复查询job状态 持续更新监控表
             * 当状态为完成/失败/中止时停止查询和更新
             */
            while (true) {
                monitorResult = HttpRequestUtil.get(setting.getJobMonitor() + "/" + jobId, null, "utf-8");
                JSONObject app = new JSONObject(monitorResult);
                JSONObject info = app.getJSONObject("app");
                monitor = Monitor.fromJSONObject(info);
                monitorService.update(monitor);
                String state = monitor.getState();
                if (state.equals(Monitor.State.FINISHED.name()) || state.equals(Monitor.State.FAILED.name())
                        || state.equals(Monitor.State.KILLED.name())) {
                    record.setFinishTime(monitor.getFinishedTime());
                    record.setState(monitor.getFinalStatus());
                    record.setResultAddress(Paths.get(setting.getJobResultPath(), record.getApplicationId()).toString());
                    JSONArray paramsDesc = new JSONArray(model.getParameters());
                    List<String> resultAddresses = new ArrayList<>();
                    for (int i = 0; i < paramsDesc.length(); i++) {
                        if (paramsDesc.getJSONObject(i).has("out"))
                            resultAddresses.add(params.getString(i));
                    }
                    record.setResultAddress(String.join(",", resultAddresses));
                    log.info("任务" + record.getApplicationId() + "完成，最终状态为:" + record.getState());
                    break;
                } else {
                    log.info("任务" + record.getApplicationId() + "正在运行...,进度为:" + monitor.getProgress());
                    Thread.sleep(setting.getMonitorInterval());
                }
            }
        } catch (Exception e) {
            log.error("任务监控失败", e);
            record.setFinishTime("" + System.currentTimeMillis());
            record.setState(Monitor.FinalStatus.FAILED.name());
        }
        try {
            taskRecordService.update(record);
        } catch (Exception e) {
            log.error("任务状态更新失败", e);
        }
        // todo 完成输出数据的元数据信息入库
    }
}
