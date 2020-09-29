package edu.zju.gis.dldsj.server.entity.workflow;

import lombok.Getter;
import lombok.Setter;
import edu.zju.gis.dldsj.server.service.ParallelModelService;

import java.util.List;

/**
 * @author Jiarui
 * @date 2020/9/24
 */

@Getter
@Setter
public class JobNodeConfig {

    private String taskId;
    private Integer retries;
    /**
     * in seconds
     */
    private Integer retryDelay;
    /**
     * in seconds
     */
    private Integer maxRetryDelay;
    /**
     * in seconds
     */
    private Integer executionTimeout;
    private Boolean dependsOnPast;
    /**
     * a json string
     */
    private String env;
    private String artifactId;
    /**
     * a json string
     */
    private List<String> params;

    private List<String> sparkSetting;

    public String buildTask(ParallelModelService parallelModelService) {
        String task = String.format("%s = BashOperator(\n" +
                "    task_id='%s',\n" +
                "    bash_command='%s',\n", taskId.replace("-", "_"), taskId, parallelModelService.getCmd(artifactId, taskId, params, sparkSetting));
        if (retries != null)
            task += String.format("    retries=%d,\n", retries);
        if (retryDelay != null)
            task += String.format("    retry_delay=timedelta(seconds=%d),\n", retryDelay);
        if (maxRetryDelay != null)
            task += String.format("    max_retry_delay=timedelta(seconds=%d),\n", maxRetryDelay);
        if (executionTimeout != null)
            task += String.format("    execution_timeout=timedelta(seconds=%d),\n", executionTimeout);
        if (dependsOnPast != null)
            task += String.format("    depends_on_past=%s,\n", dependsOnPast ? "True" : "False");
        if (env != null && !env.isEmpty())
            task += String.format("    env=%s,\n", env);
        task += "    dag=dag\n" +
                ")\n\n";
        return task;
    }
}
