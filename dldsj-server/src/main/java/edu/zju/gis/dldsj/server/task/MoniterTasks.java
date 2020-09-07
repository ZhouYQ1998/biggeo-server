package edu.zju.gis.dldsj.server.task;

import org.springframework.scheduling.annotation.Async;

/**
 * @author Jiarui
 * @date 2020/9/4
 */
public class MoniterTasks {
    @Async("taskExecutor")
    public void execute(Runnable runnable) {
        runnable.run();
    }
}
