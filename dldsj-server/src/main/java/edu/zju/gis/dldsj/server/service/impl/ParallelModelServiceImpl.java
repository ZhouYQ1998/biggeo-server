package edu.zju.gis.dldsj.server.service.impl;

import com.github.pagehelper.PageHelper;
import edu.zju.gis.dldsj.server.common.Page;
import edu.zju.gis.dldsj.server.config.CommonSetting;
import edu.zju.gis.dldsj.server.entity.ParallelModel;
import edu.zju.gis.dldsj.server.entity.ParallelModelUsage;
import edu.zju.gis.dldsj.server.entity.ParallelModelWithBLOBs;
import edu.zju.gis.dldsj.server.mapper.mysql.ParallelModelMapper;
import edu.zju.gis.dldsj.server.service.ParallelModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Keran Sun (katus)
 * @version 1.0, 2020-09-27
 */
@Service
public class ParallelModelServiceImpl implements ParallelModelService {
    @Autowired
    private CommonSetting setting;
    @Autowired
    private ParallelModelMapper parallelModelMapper;

    @Override
    public ParallelModelWithBLOBs select(String artifactId) {
        return parallelModelMapper.selectByPrimaryKey(artifactId);
    }

    @Override
    public int insert(ParallelModelWithBLOBs model) {
        return parallelModelMapper.insertSelective(model);
    }

    @Override
    public int delete(String artifactId) {
        return parallelModelMapper.deleteByPrimaryKey(artifactId);
    }

    @Override
    public Page<ParallelModel> selectWithPage(Page<ParallelModel> page) {
        PageHelper.startPage(page.getPageNo(), page.getPageSize());
        List<ParallelModel> result = parallelModelMapper.selectAll();
        return new Page<>(result);
    }

    @Override
    public Page<ParallelModel> selectByType(String type, Page<ParallelModel> page) {
        PageHelper.startPage(page.getPageNo(), page.getPageSize());
        List<ParallelModel> result = parallelModelMapper.selectByType(type);
        return new Page<>(result);
    }

    @Override
    public Page<ParallelModel> selectByUser(String userId, Page<ParallelModel> page) {
        PageHelper.startPage(page.getPageNo(), page.getPageSize());
        List<ParallelModel> result = parallelModelMapper.selectByUser(userId);
        return new Page<>(result);
    }

    @Override
    public Page<ParallelModel> search(String keywords, Page<ParallelModel> page) {
        PageHelper.startPage(page.getPageNo(), page.getPageSize());
        String[] keywordArray = keywords.split("\\s+");
        ArrayList<String> keywordsWithMark = new ArrayList<>();
        for (String keyword : keywordArray) {
            keywordsWithMark.add("%" + keyword + "%");
        }
        List<ParallelModel> result = parallelModelMapper.searchByKeywords(keywordsWithMark);
        return new Page<>(result);
    }

    @Override
    public List<ParallelModelUsage> getAllUsages() {
        return parallelModelMapper.getAllUsages();
    }

    @Override
    public String getCmd(String artifactId, String jobName, List<String> params, List<String> envSetting) {
        return getCmd(select(artifactId), jobName, params, envSetting);
    }

    @Override
    public String getCmd(ParallelModelWithBLOBs model, String jobName, List<String> params, List<String> envSetting) {
        String cmd = null;
        String driverMemory, numExecutors, executorMemory, executorCores;
        if (null != envSetting && envSetting.size() >= 4) {
            driverMemory = envSetting.get(0);
            numExecutors = envSetting.get(1);
            executorMemory = envSetting.get(2);
            executorCores = envSetting.get(3);
        } else {
            driverMemory = model.getDriverMemory();
            numExecutors = model.getNumExecutors().toString();
            executorMemory = model.getExecutorMemory();
            executorCores = model.getExecutorCores().toString();
        }
        StringBuilder paramStr = new StringBuilder();
        for (String param : params) {
            paramStr.append(param).append(" ");
        }
        if (model.getFrameworkType().equalsIgnoreCase("spark")) {
            if (model.getJarPath().split(",")[0].endsWith(".jar")) {
                cmd = setting.getSparkHome() +
                        "/bin/spark-submit" +
                        " --class " + model.getMainClass() +
                        " --master yarn --deploy-mode cluster --driver-memory " + driverMemory +
                        " --num-executors " + numExecutors +
                        " --executor-memory " + executorMemory +
                        " --executor-cores " + executorCores +
//                        " --conf spark.default.parallelism=" + model.getParallelism() +
                        " --name " + jobName + " " +
                        model.getJarPath() + " " + paramStr.toString();
            } else {//python
                cmd = setting.getSparkHome() +
                        "/bin/spark-submit" +
                        " --master yarn --deploy-mode cluster --driver-memory " + driverMemory +
                        " --num-executors " + numExecutors +
                        " --executor-memory " + executorMemory +
                        " --executor-cores " + executorCores +
//                        " --conf spark.default.parallelism=" + model.getParallelism() +
                        " --name " + jobName;
//                        " --jars " + setting.getJarPath() + "/" + queryConfig.getJar();
                if (model.getJarPath().contains(",")) {//.py,.zip
                    String[] arr = model.getJarPath().split(",");
                    cmd += " --py-files " + arr[1] + " " + arr[0];
                } else
                    cmd += " " + model.getJarPath();
                cmd += " " + paramStr.toString();
            }
        } else if (model.getFrameworkType().equalsIgnoreCase("hadoop")
                || model.getFrameworkType().equalsIgnoreCase("mapreduce")) {
            cmd = setting.getHadoopHome() + "/bin/hadoop jar " + model.getJarPath() + " " + model.getMainClass()
                    + " -D mapreduce.job.name=" + jobName
                    + " " + paramStr.toString();
        }
        return cmd;
    }
}
