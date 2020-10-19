package edu.zju.gis.dldsj.server.task;

import edu.zju.gis.dldsj.server.utils.SSHUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author Jiarui
 * @date 2020/9/3
 */

@Component
@Slf4j
public class SpiderMonitorTasks implements Runnable{

    private String path;

    @Override
    public void run() {
        log.info("AsyncService begins to execute!");
        while (true) {
            try {
                String filepath = "/Users/jiarui/学习/ZJU/暑期课程/Summer_endpoint/dldsj-server/src/main/resources/test.py";
                String cmd = "python "+filepath;
                //SSHUtil.runLocal(cmd);
                //System.out.println("cmd");
                //休眠一天，一天运行一次
                TimeUnit.HOURS.sleep(24);
            } catch (InterruptedException e) {
                log.error("AsyncService was interrupted!", e);
                return;
            }

            log.info("AsyncService execution completed!");
        }
    }
}
