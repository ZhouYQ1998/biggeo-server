package edu.zju.gis.dldsj.server.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @author Hu
 * @date 2019/4/27
 **/
@Slf4j
public final class HttpUtil {
    private final static int UPLOAD_FILESIZE_LIMIT = 1073741824;

    /**
     * 获取Post请求的请求体
     *
     * @param request
     * @return
     */
    public static String getRequestBody(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            inputStream = request.getInputStream();
            reader = new BufferedReader(
                    new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            if (inputStream.available() > 0) {
                String len = reader.readLine();
                while (len != null) {
                    sb.append(len);
                    len = reader.readLine();
                }
            }
        } catch (IOException e) {
            log.error("Stream Closed");
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }


    /**
     * 获取客户端ip地址
     *
     * @param request
     * @return
     */
    public static String getCliectIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.trim() == "" || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.trim() == "" || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.trim() == "" || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        // 多个路由时，取第一个非unknown的ip
        final String[] arr = ip.split(",");
        for (final String str : arr) {
            if (!"unknown".equalsIgnoreCase(str)) {
                ip = str;
                break;
            }
        }
        return ip;
    }

    /**
     * 判断是否为ajax请求
     *
     * @param request
     * @return
     */
    public static String getRequestType(HttpServletRequest request) {
        return request.getHeader("X-Requested-With");
    }

    /**
     * 判断请求上传文件是否符合标准
     *
     * @param
     * @return
     */
    public static boolean ifUploadFileIsAllowed(List<MultipartFile> files) {
        for (int i = 0; i < files.size(); i++) {
            if (files.get(i).getSize() > UPLOAD_FILESIZE_LIMIT)
                return false;
        }
        return true;
    }

    public static void DownloadErrorReport(HttpServletResponse response, String error) {
        try (PrintWriter writer = response.getWriter()) {
            writer.println("文件下载失败：");
            writer.println(error);
        } catch (IOException ex) {
            log.error("系统错误", ex);
        }
    }
}