package edu.zju.gis.dldsj.server.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author zyq
 * @date 2020/10/23
 */
public class CrawlerUtil {

    public static String[] crawl(String[] urls, String userAgent){
        String[] htmls = new String[urls.length];
        for (int i=0; i<urls.length; i++) {
            try{
                URL url = new URL(urls[i]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("accept", "*/*");
                connection.setRequestProperty("connection", "Keep-Alive");
                connection.setRequestProperty("user-agent", userAgent);
                connection.connect();
                if(connection.getResponseCode() == 200){
                    InputStream is = connection.getInputStream();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] buffer = new byte[10485760];
                    int len;
                    while ((len = is.read(buffer)) != -1){
                        baos.write(buffer, 0, len);
                    }
                    htmls[i] = baos.toString();
                    baos.close();
                    is.close();
                }
                else{
                    htmls[i] = "error: " + connection.getResponseCode() + " " + connection.getResponseMessage();
                }
            }catch (Exception e){
                htmls[i] = "error: " + e.getMessage();
            }
        }
        return htmls;
    }

}
