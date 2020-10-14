package edu.zju.gis.dldsj.server.utils;

import edu.zju.gis.dldsj.server.utils.fs.FsManipulator;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 上传 下载工具类
 * @author Keran Sun (katus)
 * @version 1.0, 2020-09-30
 */
public final class LoadUtils {
    public static void download(FsManipulator fsManipulator, String filePath, String filename, HttpServletResponse res) throws IOException {
        res.setContentType("application/octet-stream");
        // 防止中文名乱码
        res.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes(), "ISO8859-1"));
        InputStream is = fsManipulator.read(filePath);
        res.addHeader("Content-Length", String.valueOf(is.available()));
        OutputStream os = res.getOutputStream();
        fsManipulator.copyBytes(is, os);
    }
}
