package edu.zju.gis.dldsj.server.utils;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import edu.zju.gis.dldsj.server.utils.fs.FsManipulator;
import edu.zju.gis.dldsj.server.utils.fs.FsManipulatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.util.Collections;

/**
 * 调用SSH并运行命令
 *
 * @author HLS
 */
public final class SSHUtil {
    private static final Logger log = LoggerFactory.getLogger(SSHUtil.class);
    private static final String ENCODE_SET = "export LC_CTYPE=zh_CN.UTF-8;";

    /**
     * return a opened Connection
     */
    private static Connection getOpenedConnection(String host, String username, String password) throws IOException {

        Connection conn = new Connection(host);
        conn.connect(); // make sure the connection is opened
        boolean isAuthenticated = conn.authenticateWithPassword(username, password);
        if (!isAuthenticated)
            throw new IOException("Authentication failed.");
        return conn;
    }

    /**
     * Run SSH command.调用ssh服务
     */
    public static void runSSH(String host, String username, String password,
                              String cmd, String tempShFile) throws IOException {
        Connection conn = getOpenedConnection(host, username, password);
        Session session = conn.openSession();
        long startTime = System.currentTimeMillis();
        log.info(String.format("正在等待写入命令（长度%d）：%s\n", cmd.length(), cmd));
        if (cmd.length() > 10000) {
            File tempSh = new File(tempShFile, "tempJob.sh");
            FsManipulator fsManipulator = FsManipulatorFactory.create();
            if (tempSh.exists())
                fsManipulator.deleteFile(tempSh.getAbsolutePath());
            Files.write(tempSh.toPath(), Collections.singletonList(cmd));
            session.execCommand("echo " + cmd + " > " + tempSh.getAbsolutePath() +
                    "&&chmod 777 " + tempSh.getAbsolutePath() +
                    "&&" + ENCODE_SET + tempSh.getAbsolutePath() +
                    ";rm " + tempSh.getAbsolutePath());
        } else {
            session.execCommand(ENCODE_SET + cmd);
        }
        log.info("此命令共耗时:" + (System.currentTimeMillis() - startTime) + "ms");
        //关闭session
        session.close();
        //关闭connection
        conn.close();
    }

    /**
     * 调用远程SSH并且获得输出结果
     */
    public static String runSSHWithOutput(String host, String username, String password,
                                          String cmd, String tempShFile) throws IOException {
        Connection conn = getOpenedConnection(host, username, password);
        Session session = conn.openSession();
        long startTime = System.currentTimeMillis();
        log.info(String.format("正在等待写入命令（长度%d）：%s\n", cmd.length(), cmd));
        if (cmd.length() > 10000) {
            File tempSh = new File(tempShFile, "tempJob" + startTime + ".sh");
            FsManipulator fsManipulator = FsManipulatorFactory.create();
            if (tempSh.exists())
                fsManipulator.deleteFile(tempSh.getAbsolutePath());
            session.execCommand("echo " + cmd + " > " + tempSh.getAbsolutePath() + "&&chmod 777 " + tempSh.getAbsolutePath() + "&&" + ENCODE_SET + tempSh.getAbsolutePath());
        } else {
            session.execCommand(ENCODE_SET + cmd);
        }
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new StreamGobbler(session.getStdout())))) {
            String line;
            while ((line = br.readLine()) != null)
                sb.append(line).append("\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        log.info("输出结果为:" + sb.toString());
        log.info("此命令共耗时:" + (System.currentTimeMillis() - startTime) + "ms");
        session.close();
        conn.close();
        return sb.toString();
    }

    /**
     * Run local command 调用本地的command
     *
     * @param cmd
     * @return exit status
     */
    public static int runLocal(String cmd) throws IOException {
        Runtime rt = Runtime.getRuntime();
        Process p = rt.exec(cmd);

        InputStream stdout = new StreamGobbler(p.getInputStream());
        BufferedReader br = new BufferedReader(new InputStreamReader(stdout));

        while (true) {
            String line = br.readLine();
            if (line == null)
                break;

        }
        br.close();
        return p.exitValue();
    }
}
