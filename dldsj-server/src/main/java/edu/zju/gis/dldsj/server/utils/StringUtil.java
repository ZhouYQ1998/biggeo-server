package edu.zju.gis.dldsj.server.utils;

import com.google.gson.Gson;
import scala.math.Ordering;

import java.util.List;
import java.util.Map;

/**
 * @author yanlo yanlong_lee@qq.com
 * @version 1.0 2018/08/07
 */
public class StringUtil {
    /**
     * i_am_camel => iAmCamel
     */
    public static String camelCase(String value) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < value.length(); i++) {
            char ch = value.charAt(i);
            if (ch == '_') {
                i++;
                if (i < value.length())
                    sb.append((char) (value.charAt(i) - 'a' + 'A'));
            } else
                sb.append(ch);
        }
        return sb.toString();
    }

    /**
     * iAmCamel => i_am_camel
     */
    public static String unCamelCase(String value) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < value.length(); i++) {
            char ch = value.charAt(i);
            if (ch >= 'A' && ch <= 'Z') {
                sb.append('_').append((char) (ch - 'A' + 'a'));
            } else
                sb.append(ch);
        }
        return sb.toString();
    }

    /**
     * Map 转 String
     *
     * @param map
     * @return
     */
    public static String toString(Map map) {
        Gson gson = new Gson();
        return gson.toJson(map);
    }

    /**
     * StringList 转 String
     *
     * @param list
     * @return
     */
    public static String toCsvString(List<String> list) {
        StringBuilder sbd = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sbd.append(list.get(i) + ",");
        }
        String end = sbd.toString();
        return end.substring(0, end.length() - 1);
    }

}
