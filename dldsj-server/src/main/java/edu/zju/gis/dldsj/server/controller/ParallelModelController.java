package edu.zju.gis.dldsj.server.controller;

import edu.zju.gis.dldsj.server.common.Page;
import edu.zju.gis.dldsj.server.common.Result;
import edu.zju.gis.dldsj.server.config.CommonSetting;
import edu.zju.gis.dldsj.server.constant.CodeConstants;
import edu.zju.gis.dldsj.server.constant.CodeType;
import edu.zju.gis.dldsj.server.entity.*;
import edu.zju.gis.dldsj.server.service.MonitorService;
import edu.zju.gis.dldsj.server.service.ParallelModelService;
import edu.zju.gis.dldsj.server.service.TaskRecordService;
import edu.zju.gis.dldsj.server.service.UserService;
import edu.zju.gis.dldsj.server.task.MonitorTasks;
import edu.zju.gis.dldsj.server.task.TaskMonitor;
import edu.zju.gis.dldsj.server.utils.LoadUtils;
import edu.zju.gis.dldsj.server.utils.SSHUtil;
import edu.zju.gis.dldsj.server.utils.XMLReader;
import edu.zju.gis.dldsj.server.utils.fs.FsManipulator;
import edu.zju.gis.dldsj.server.utils.fs.FsManipulatorFactory;
import edu.zju.gis.dldsj.server.utils.fs.LocalFsManipulator;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Keran Sun (katus)
 * @version 1.0, 2020-09-25
 */
@Slf4j
@RestController
@RequestMapping("/parallel")
public class ParallelModelController {
    @Autowired
    private CommonSetting setting;

    @Autowired
    private ParallelModelService parallelModelService;

    @Autowired
    private TaskRecordService taskRecordService;

    @Autowired
    private MonitorService monitorService;

    @Autowired
    private UserService userService;

    @Autowired
    private MonitorTasks monitorTasks;

    @Value("${spring.mvc.static-path-pattern}")
    private String staticPathPattern;   // mvc静态资源请求目录

    @Value("${uri.hdfs}")
    private String hdfsUri;   // hdfs 路径

    /**
     * 模型注册
     * @param userId 用户ID 取自Session属性
     * @param request Servlet请求
     * @return 标准结果体
     */
    @PostMapping(value = "/register")
    public Result<String> register(@SessionAttribute("userId") String userId, HttpServletRequest request) {
        Result<String> result = new Result<>();
        // 如果请求数据中不包括文件
        if (!ServletFileUpload.isMultipartContent(request)) {   // 判断是否是包含文件的表单数据
            return result.setCode(CodeConstants.VALIDATE_ERROR).setBody("ERROR")
                    .setMessage("上传文件必须有xml、jar、图片(png,jpg)三个文件或xml、py、zip(可选)、图片(png,jpg)四个文件");
        }
        // 如果请求数据中包含文件
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");   // 获取name为file的文件
        MultipartFile jarFile = null, xmlFile = null, picFile = null, zipFile = null;
        CodeType codeType = CodeType.NONE;
        for (MultipartFile file : files) {
            String filename = file.getOriginalFilename();
            if (!file.isEmpty() && filename != null) {
                String ext = filename.substring(filename.lastIndexOf(".")).toLowerCase();
                switch (ext) {
                    case ".jar":
                        jarFile = file;
                        codeType = CodeType.JAVA;
                        break;
                    case ".py":
                        jarFile = file;
                        codeType = CodeType.PYTHON;
                        break;
                    case ".xml":
                        xmlFile = file;
                        break;
                    case ".png":
                    case ".jpg":
                        picFile = file;
                        break;
                    case ".zip":
                    case ".egg":
                        zipFile = file;
                        break;
                }
            }
        }
        // 如果请求数据中的必要文件不存在或上传失败
        if (codeType == CodeType.NONE || xmlFile == null) {
            return result.setCode(CodeConstants.VALIDATE_ERROR).setBody("ERROR")
                    .setMessage("xml与程序文件(.py/.jar)未上传或上传失败");
        }
        FsManipulator fsManipulator = FsManipulatorFactory.create();
        try {
            // 在临时目录解析xml文件
            File tmpXml = new File(setting.getTemplatePath(), UUID.randomUUID().toString() + ".xml");
            xmlFile.transferTo(tmpXml);
            ParallelModelWithBLOBs model = XMLReader.readParallelModel(tmpXml);
            if (model.getArtifactId() == null || model.getArtifactId().isEmpty()) {
                return result.setCode(CodeConstants.VALIDATE_ERROR).setBody("ERROR")
                        .setMessage("xml文件必须包含artifactId字段");
            }
            // 将符合规范的xml文件转储到xml目录
            File xmlPath = new File(setting.getXmlPath(), model.getArtifactId() + ".xml");
            if (xmlPath.exists()) {
                fsManipulator.deleteFile(xmlPath.getAbsolutePath());
            }
            fsManipulator.rename(tmpXml.getAbsolutePath(), xmlPath.getAbsolutePath());   // 实际上是移动文件
            model.setXmlPath(xmlPath.getAbsolutePath());
            // 存储可执行代码文件
            String ext = codeType == CodeType.PYTHON ? ".py" : ".jar";
            File jarPath = new File(setting.getJarPath(), model.getArtifactId() + ext);
            if (jarPath.exists()) {
                fsManipulator.deleteFile(jarPath.getAbsolutePath());
            }
            jarFile.transferTo(jarPath);
            model.setJarPath(jarPath.getAbsolutePath());
            // 存储图片文件
            if (picFile != null) {
                String picName = picFile.getOriginalFilename();
                picName = picName == null ? ".png" : picName.substring(picName.lastIndexOf("."));
                File picPath = new File(setting.getPicPath(), model.getArtifactId() + picName);
                if (picPath.exists()) {
                    fsManipulator.deleteFile(picPath.getAbsolutePath());
                }
                picFile.transferTo(picPath);   // 在服务器文件系统中存了一份
                model.setPicPath(picPath.getAbsolutePath());
                // todo 关于静态资源路径的问题
//                String picUrl = staticPathPattern.replace("*", "") + picPath.getName();
//                model.setPicPath(picUrl);   // 存入资源静态路径
            }
            // 存储压缩文件
            if (zipFile != null) {
                String zipName = zipFile.getOriginalFilename();
                zipName = zipName == null ? ".zip" : zipName.substring(zipName.lastIndexOf("."));
                File zipPath = new File(setting.getJarPath(), model.getArtifactId() + zipName);
                if (zipPath.exists()) {
                    fsManipulator.deleteFile(zipPath.getAbsolutePath());
                }
                zipFile.transferTo(zipPath);
                model.setJarPath(model.getJarPath() + "," + zipPath.getAbsolutePath());
            }
            String paths = model.getXmlPath() + "," + model.getJarPath() + "," + model.getPicPath();
            log.info("文件上传完成, 保存路径为: " + paths);
            model.setUserId(userId);
            model.setIsPublic(false);
            parallelModelService.insert(model);
            result.setCode(CodeConstants.SUCCESS).setBody(paths).setMessage("模型注册成功");
        } catch (Exception e) {
            log.error("模型注册失败", e);
            result.setCode(CodeConstants.SERVICE_ERROR).setBody("ERROR").setMessage("模型注册失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 模型注销
     * @param userId 用户ID 取自Session属性
     * @param artifactId 模型ID
     * @return 标准结果体
     */
    @DeleteMapping(value = "/unregister/{artifactId}")
    public Result<String> unregister(@SessionAttribute("userId") String userId, @PathVariable String artifactId) {
        Result<String> result = new Result<>();
        FsManipulator fsManipulator = FsManipulatorFactory.create();
        try {
            ParallelModelWithBLOBs model = parallelModelService.select(artifactId);
            if (userId.equals(model.getUserId())) {
                parallelModelService.delete(artifactId);
                // 删除模型在服务器本地保存的文件
                fsManipulator.deleteFiles(model.getJarPath().split(","));
                fsManipulator.deleteFile(model.getXmlPath());
                fsManipulator.deleteFile(model.getPicPath());
                // todo 关于静态资源目录的适配
//                fsManipulator.deleteFile(model.getPicPath().replace("static/", ""));
                result.setCode(CodeConstants.SUCCESS).setBody("SUCCESS").setMessage("模型注销成功");
            } else {
                result.setCode(CodeConstants.USER_PERMISSION_ERROR).setBody("ERROR").setMessage("不能删除他人的模型");
            }
        } catch (Exception e) {
            log.error("模型注销失败", e);
            result.setCode(CodeConstants.DAO_ERROR).setBody("ERROR").setMessage("模型注销失败: " + e.getMessage());
        }
        return result;
    }

    /**
     * 模型下载
     * @param userId 用户ID 取自Session属性
     * @param artifactId 模型ID
     * @param response Servlet响应
     * @return 标准结果体
     */
    @PostMapping(value = "/download/{artifactId}")
    public Result<String> download(@SessionAttribute("userId") String userId, @PathVariable String artifactId, HttpServletResponse response) {
        Result<String> result = new Result<>();
        FsManipulator fsManipulator = FsManipulatorFactory.create();
        try {
            // 根据artifactId获取模型信息
            ParallelModelWithBLOBs model = parallelModelService.select(artifactId);
            // todo 根据userId获取是否是管理员身份
            boolean isAdmin = false;
            if (model.getIsPublic() || isAdmin) {
                // 将模型相关文件打包压缩成一个文件
                String tempZipPath = new File(setting.getTemplatePath(), artifactId + "-" + UUID.randomUUID().toString() + ".zip").getAbsolutePath();
                String wholePath = model.getXmlPath() + "," + model.getJarPath() + "," + model.getPicPath();
                ((LocalFsManipulator) fsManipulator).compress(wholePath.split(","), tempZipPath);
                // 执行下载操作IO
                LoadUtils.download(fsManipulator, tempZipPath, artifactId + ".zip", response);
                // 删除本地临时文件
                fsManipulator.deleteFile(tempZipPath);
                return null;
            } else {
                result.setCode(CodeConstants.USER_PERMISSION_ERROR).setBody("ERROR").setMessage("不能下载未公开的模型");
            }
        } catch (Exception e) {
            log.error("模型下载失败", e);
            result.setCode(CodeConstants.DAO_ERROR).setBody("ERROR").setMessage("模型下载失败: " + e.getMessage());
        }
        return result;
    }

    /**
     * 分页获取全部模型信息
     * @param page 页面参数
     * @return 标准结果体
     */
    @GetMapping(value = "/get")
    public Result<Page<ParallelModel>> getAllModels(Page<ParallelModel> page) {
        Result<Page<ParallelModel>> result = new Result<>();
        try {
            Page<ParallelModel> resultPage = parallelModelService.selectWithPage(page);
            result.setCode(CodeConstants.SUCCESS).setBody(resultPage).setMessage("模型获取成功");
        } catch (Exception e) {
            log.error("模型获取失败", e);
            result.setCode(CodeConstants.DAO_ERROR).setMessage("模型获取失败: " + e.getMessage());
        }
        return result;
    }

    /**
     * 根据模型ID获取模型详细信息
     * @param artifactId 模型ID
     * @return 标准结果体
     */
    @GetMapping(value = "/get/{artifactId}")
    public Result<ParallelModelWithBLOBs> getModel(@PathVariable String artifactId) {
        Result<ParallelModelWithBLOBs> result = new Result<>();
        try {
            ParallelModelWithBLOBs model = parallelModelService.select(artifactId);
            result.setCode(CodeConstants.SUCCESS).setBody(model).setMessage("模型获取成功");
        } catch (Exception e) {
            log.error("模型获取失败", e);
            result.setCode(CodeConstants.DAO_ERROR).setMessage("模型获取失败: " + e.getMessage());
        }
        return result;
    }

    /**
     * 根据Usage分页获取模型信息
     * @param usage USAGE
     * @param page 分页参数
     * @return 标准结果体
     */
    @GetMapping(value = "/getType/{usage}")
    public Result<Page<ParallelModel>> getModelsByType(@PathVariable String usage, Page<ParallelModel> page) {
        Result<Page<ParallelModel>> result = new Result<>();
        try {
            Page<ParallelModel> resultPage = parallelModelService.selectByType(usage, page);
            result.setCode(CodeConstants.SUCCESS).setBody(resultPage).setMessage("模型获取成功");
        } catch (Exception e) {
            log.error("模型获取失败", e);
            result.setCode(CodeConstants.DAO_ERROR).setMessage("模型获取失败: " + e.getMessage());
        }
        return result;
    }

    /**
     * 根据UserId分页获取模型信息
     * @param userId 用户ID 取自Session属性
     * @param page 分页参数
     * @return 标准结果体
     */
    @GetMapping(value = "/getMine")
    public Result<Page<ParallelModel>> getMyModels(@SessionAttribute("userId") String userId, Page<ParallelModel> page) {
        Result<Page<ParallelModel>> result = new Result<>();
        try {
            Page<ParallelModel> resultPage = parallelModelService.selectByUser(userId, page);
            result.setCode(CodeConstants.SUCCESS).setBody(resultPage).setMessage("模型获取成功");
        } catch (Exception e) {
            log.error("模型获取失败", e);
            result.setCode(CodeConstants.DAO_ERROR).setMessage("模型获取失败: " + e.getMessage());
        }
        return result;
    }

    /**
     * 根据关键词搜索模型
     * @param keywords 关键词 (支持空白符号分割)
     * @param page 分页参数
     * @return 标准结果体
     */
    @GetMapping(value = "/search")
    public Result<Page<ParallelModel>> searchModels(String keywords, Page<ParallelModel> page) {
        Result<Page<ParallelModel>> result = new Result<>();
        try {
            Page<ParallelModel> modelPage = parallelModelService.search(keywords, page);
            result.setCode(CodeConstants.SUCCESS).setBody(modelPage).setMessage("模型查询成功");
        } catch (Exception e) {
            log.error("模型查询失败", e);
            result.setCode(CodeConstants.DAO_ERROR).setMessage("模型查询失败: " + e.getMessage());
        }
        return result;
    }

    /**
     * 获取全部的USAGE名称
     * @return 标准结果体
     */
    @GetMapping(value = "/getAllUsages")
    public Result<List<ParallelModelUsage>> getAllUsages() {
        Result<List<ParallelModelUsage>> result = new Result<>();
        try {
            List<ParallelModelUsage> usageList = parallelModelService.getAllUsages();
            result.setCode(CodeConstants.SUCCESS).setBody(usageList).setMessage("模型全部用途获取成功");
        } catch (Exception e) {
            log.error("模型全部用途获取失败", e);
            result.setCode(CodeConstants.DAO_ERROR).setMessage("模型全部用途获取失败: " + e.getMessage());
        }
        return result;
    }

    /**
     * 运行单个模型 (同时开启监控线程)
     * @param userName 用户名 取自Session属性
     * @param userId 用户ID 取自Session属性
     * @param artifactId 模型ID
     * @param requestBody 请求体 (json形式的string)
     * @return 标准结果体
     */
    @PostMapping(value = "/run/{artifactId}")
    public Result<String> run(@SessionAttribute("userName") String userName, @SessionAttribute("userId") String userId,
                              @PathVariable String artifactId, @RequestBody String requestBody) {
        Result<String> result = new Result<>();
        try {
            ParallelModelWithBLOBs model = parallelModelService.select(artifactId);
            if (model == null) {
                result.setCode(CodeConstants.VALIDATE_ERROR).setBody("ERROR")
                        .setMessage("找不到artifactId为`" + artifactId + "`的模型");
                return result;
            }
            result = postParallelModelTask(model, userName, userId, requestBody);
        } catch (Exception e) {
            result.setCode(CodeConstants.VALIDATE_ERROR).setBody("ERROR").setMessage("数据下拉或提交任务失败");
        }
        return result;
    }

    /**
     * 中止任务
     * @param userName 用户名 取自Session属性
     * @param jobName 任务名称
     * @return 标准结果体
     */
    @PostMapping(value = "/jobs/kill/{jobName}")
    public Result<String> killJob(@SessionAttribute("userName") String userName, @PathVariable String jobName) {
        Result<String> result = new Result<>();
        try {
            TaskRecord record = taskRecordService.select(jobName);
            if (record.getCreateUser().equals(userName)) {
                return result.setCode(CodeConstants.USER_PERMISSION_ERROR).setBody("ERROR")
                        .setMessage("不能中止其他用户的任务");
            }
            Monitor monitor = monitorService.getByName(jobName);
            String applicationId = monitor.getId();
            String cmd = setting.getHadoopHome() + "/lib/spark2/yarn application -kill " + applicationId;
            log.info("尝试kill任务: " + cmd);
            SSHUtil.runSSH(setting.getNameNode(), setting.getUsername(), setting.getPassword(), cmd, setting.getParallelFilePath());
            monitor.setState(Monitor.FinalStatus.KILLED.name());
            monitorService.update(monitor);
            result.setCode(CodeConstants.SUCCESS).setBody("SUCCESS").setMessage("任务中止成功");
        } catch (Exception e) {
            log.error("任务中止失败", e);
            result.setCode(CodeConstants.SERVICE_ERROR).setBody("ERROR").setMessage("任务中止失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 获取全部任务列表 仅限管理员
     * @param page 分页参数
     * @return 标准结果体
     */
    @GetMapping(value = "/jobs/get")
    public Result<Page<TaskRecord>> getAllJobs(Page<TaskRecord> page) {
        Result<Page<TaskRecord>> result = new Result<>();
        try {
            Page<TaskRecord> resultPage = taskRecordService.getAll(page);
            result.setCode(CodeConstants.SUCCESS).setBody(resultPage).setMessage("任务获取成功");
        } catch (Exception e) {
            log.error("任务获取失败", e);
            result.setCode(CodeConstants.DAO_ERROR).setMessage("任务获取失败: " + e.getMessage());
        }
        return result;
    }

    /**
     * 根据任务名称获取任务信息
     * @param jobName 任务名称
     * @return 标准结果体
     */
    @GetMapping(value = "/jobs/get/{jobName}")
    public Result<TaskRecord> getJob(@PathVariable String jobName) {
        Result<TaskRecord> result = new Result<>();
        try {
            TaskRecord record = taskRecordService.select(jobName);
            result.setCode(CodeConstants.SUCCESS).setBody(record).setMessage("任务获取成功");
        } catch (Exception e) {
            log.error("任务获取失败", e);
            result.setCode(CodeConstants.DAO_ERROR).setMessage("任务获取失败: " + e.getMessage());
        }
        return result;
    }

    /**
     * 分页获取当前用户的任务信息
     * @param userName 用户名 取自Session属性
     * @param page 分页参数
     * @return 标准结果体
     */
    @GetMapping(value = "/jobs/getMine")
    public Result<Page<TaskRecord>> getMyJobs(@SessionAttribute("userName") String userName, Page<TaskRecord> page) {
        Result<Page<TaskRecord>> result = new Result<>();
        try {
            Page<TaskRecord> resultPage = taskRecordService.getPagesByUser(userName, page);
            result.setCode(CodeConstants.SUCCESS).setBody(resultPage).setMessage("任务获取成功");
        } catch (Exception e) {
            log.error("任务获取失败", e);
            result.setCode(CodeConstants.DAO_ERROR).setMessage("任务获取失败: " + e.getMessage());
        }
        return result;
    }

    /**
     * 分页获取当前用户指定状态的任务信息
     * @param userName 用户名 取自Session属性
     * @param page 分页参数
     * @param status 状态 UNDEFINED, SUCCEEDED, FAILED, KILLED
     * @return 标准结果体
     */
    @GetMapping(value = "/jobs/getMine/{status}")
    public Result<Page<TaskRecord>> getMyJobsByStatus(@SessionAttribute("userName") String userName, Page<TaskRecord> page, @PathVariable Monitor.FinalStatus status) {
        Result<Page<TaskRecord>> result = new Result<>();
        try {
            Page<TaskRecord> resultPage = taskRecordService.getPagesByUserWithState(userName, page, status);
            result.setCode(CodeConstants.SUCCESS).setBody(resultPage).setMessage("任务获取成功");
        } catch (Exception e) {
            log.error("任务获取失败", e);
            result.setCode(CodeConstants.DAO_ERROR).setMessage("任务获取失败: " + e.getMessage());
        }
        return result;
    }

    /**
     * 根据任务名称获取监控信息
     * @param jobName 任务名称
     * @return 标准结果体
     */
    @GetMapping(value = "/monitor/get/{jobName}")
    public Result<Monitor> getMonitor(@PathVariable String jobName) {
        Result<Monitor> result = new Result<>();
        try {
            Monitor monitor = monitorService.getByName(jobName);
            result.setBody(monitor).setCode(CodeConstants.SUCCESS).setMessage("监控信息获取成功");
        } catch (Exception e) {
            log.error("监控信息获取失败", e);
            result.setCode(CodeConstants.DAO_ERROR).setMessage("监控信息获取失败: " + e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/test/{artifactId}", method = RequestMethod.POST)
    public String test(@PathVariable String artifactId, @RequestBody String requestBody) {
        System.out.println(requestBody);
        System.out.println(artifactId);
        return "success";
    }

    private void submitTask(String jobName, String userId, String userName, ParallelModelWithBLOBs model, JSONArray params, JSONArray spatialOut, String tag) {
        TaskRecord record = new TaskRecord();
        record.setApplicationId(jobName);
        record.setToolId(model.getArtifactId());
        record.setCreateUser(userName);
        record.setSubmitTime(String.valueOf(System.currentTimeMillis()));
        record.setState(Monitor.FinalStatus.UNDEFINED.name());
        record.setRemarks(tag);
        List<List<String>> fieldNames = new ArrayList<>();
        if (!model.getOut().trim().isEmpty()) {
            JSONArray outputs = new JSONArray(model.getOut());
            for (int f = 0; f < outputs.length(); f++) {
                List<String> eachFieldNames = new ArrayList<>();
                JSONObject output = outputs.getJSONObject(f);
                JSONArray fields = output.optJSONArray("fields");
                for (int i = 0; i < fields.length(); i++) {
                    JSONObject field = fields.getJSONObject(i);
                    String name = field.optString("name");
                    if (!name.isEmpty()) {
                        eachFieldNames.add(name);
                    }
                }
                fieldNames.add(eachFieldNames);
            }
        }
        StringBuilder outPutType = new StringBuilder();
        if (!fieldNames.isEmpty()) {
            for (List<String> fieldName : fieldNames) {
                outPutType.append(String.join(",", fieldName)).append(";");
            }
        }
        record.setOutputType((outPutType.length() == 0) ? "file" : outPutType.substring(0, outPutType.length() - 1));
        record.setParams(params.join(","));
        taskRecordService.insert(record);
        monitorTasks.execute(new TaskMonitor(setting, userId, taskRecordService, monitorService
                , userService/*, userDataService, userDataFieldService*/
                , record, model, params, spatialOut, true));
    }

    private Result<String> postParallelModelTask(ParallelModelWithBLOBs model, String userName, String userId, String requestBody) {
        Result<String> result = new Result<>();
        JSONObject inputs = new JSONObject(requestBody);
        String remarks = inputs.optString("customName");
        JSONArray params = inputs.getJSONArray("params");
        //需要空间化的计算结果
        JSONArray spatialOut = inputs.getJSONArray("spatialOut");
        String jobName = model.getArtifactId() + "-" + System.currentTimeMillis();

        JSONArray modelParam = new JSONArray(model.getParameters());
        for (int i = 0; i < params.length(); i++) {
            JSONObject obj = modelParam.getJSONObject(i);
            if (obj.has("out") || obj.has("in")) {
                String path = params.getString(i);
                params.put(i, Paths.get(setting.getUserSpaceRootPath(), userId, path).toString());
            }
        }
        List<String> modelParams = new ArrayList<>();
        for (int i = 0; i < params.length(); i++) {
            modelParams.add(params.getString(i));
        }

        String cmd = parallelModelService.getCmd(model, jobName, modelParams, null);
        if (cmd == null) {
            result.setCode(CodeConstants.SYSTEM_ERROR).setBody("ERROR").setMessage("不支持的模型运行框架：" + model.getFrameworkType());
        } else {
            try {
                SSHUtil.runSSH(setting.getNameNode(), setting.getUsername(), setting.getPassword(), cmd, setting.getParallelFilePath());
                log.info("a new application is submitted by " + userName + " with cmd[" + cmd + "]");
                submitTask(jobName, userId, userName, model, params, spatialOut, remarks);
                result.setCode(CodeConstants.SUCCESS).setBody("SUCCESS").setBody(jobName);
            } catch (Exception e) {
                log.error("任务提交失败", e);
                result.setCode(CodeConstants.SYSTEM_ERROR).setBody("ERROR").setMessage("任务提交失败" + e.getMessage());
            }
        }
        return result;
    }
}
