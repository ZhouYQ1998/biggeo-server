package edu.zju.gis.dldsj.server.task;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author Jiarui, Keran Sun (katus)
 * @date 2020/9/4
 * @version v2.0
 */
@Component
public class MonitorTasks {
    @Async("taskExecutor")
    public void execute(Runnable runnable) {
        runnable.run();
    }

    @Async
    public void getThreadName() {
        System.out.println(Thread.currentThread().getName());
    }
}
