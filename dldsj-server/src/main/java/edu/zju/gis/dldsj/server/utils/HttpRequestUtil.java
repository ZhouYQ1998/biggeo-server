package edu.zju.gis.dldsj.server.utils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class HttpRequestUtil {

    private static String resolveEntity(HttpEntity entity, String encoding) throws IOException {
        StringBuilder result = new StringBuilder();
        if (entity != null) {
            try (InputStream is = entity.getContent()) {
                int length;
                byte[] buff = new byte[8192];
                while ((length = is.read(buff)) != -1) {
                    result.append(new String(buff, 0, length, encoding));
                }
            }
        }
        return result.toString();
    }

    public static String get(String url, Map<String, String> params, String outEncoding) throws IOException {
        if (params != null && !params.isEmpty()) {
            if (url.contains("?")) {
                url += "&";
            } else {
                url += "?";
            }
            StringBuilder buffer = new StringBuilder();
            for (String key : params.keySet()) {
                buffer.append(key).append("=").append(params.get(key)).append("&");
            }
            url += buffer.toString();
        }
        String result;
        try (CloseableHttpResponse response = HttpClients.createDefault().execute(new HttpGet(url))) {
            result = resolveEntity(response.getEntity(), outEncoding);
        }
        return result;
    }

    public static String post(String url, Map<String, String> params, String encoding, String outEncoding) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        if (params != null) {
            List<NameValuePair> paramList = new ArrayList<>();
            params.forEach((k, v) -> {
                if (k != null)
                    paramList.add(new BasicNameValuePair(k, v));
            });
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, encoding);
            httpPost.setEntity(entity);
        }
        String result;
        try (CloseableHttpResponse response = HttpClients.createDefault().execute(httpPost)) {
            result = resolveEntity(response.getEntity(), outEncoding);
        }
        return result;
    }

    public static String postByJson(String url, String params, String encoding, String outEncoding) throws IOException {
        // 以POST方式请求
        HttpPost httpPost = new HttpPost(url);
        // 加入请求参数
        if (params != null) {
            HttpEntity entity = new StringEntity(params, encoding);
            httpPost.setEntity(entity);
        }
        String result;
        try (CloseableHttpResponse response = HttpClients.createDefault().execute(httpPost)) {
            result = resolveEntity(response.getEntity(), outEncoding);
        }
        return result;
    }

    public static String getByJson(String url, String params, String outEncoding) throws IOException {
        if (params != null) {
            if (url.contains("?")) {
                url += "&";
            } else {
                url += "?";
            }
            StringBuilder buffer = new StringBuilder();
            JSONObject json = new JSONObject(params);
            for (String key : json.keySet())
                buffer.append(key).append("=").append(json.get(key)).append("&");
            url += buffer.toString();
        }
        String result;
        try (CloseableHttpResponse response = HttpClients.createDefault().execute(new HttpGet(url))) {
            result = resolveEntity(response.getEntity(), outEncoding);
        }
        return result;
    }

}