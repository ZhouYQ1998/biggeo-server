package edu.zju.gis.dldsj.server.utils;

import ch.ethz.ssh2.StreamGobbler;

import java.io.*;

/**
 * @author Jiarui
 * @date 2020/9/3
 */

/**
 * Run local command 调用本地的command
 *
 * @return exit status
 */
public class CmdUtil {

    public static int runLocal(String cmd) throws IOException, InterruptedException {
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
        int r = p.waitFor();
        return r;
    }
}
