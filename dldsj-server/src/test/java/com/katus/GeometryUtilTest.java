package com.katus;

import com.vividsolutions.jts.io.ParseException;
import edu.zju.gis.dldsj.server.utils.GeometryUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Keran Sun (katus)
 * @version 1.0, 2020-11-10
 */
public class GeometryUtilTest {
    public static void main(String[] args) throws IOException, ParseException {
        List<String> list = new ArrayList<>();
        list.add("MULTIPOLYGON (((121.744855399 29.935543142,121.744979 29.9355563,121.745719 29.935632201,121.745730501 29.9355712440001,121.7454923 29.935537751,121.745134601 29.9354847010001,121.744632353 29.93541695,121.7443905 29.935386,121.744313351 29.935375751,121.7442999 29.935442352,121.744309854 29.9355019490001,121.74443065 29.935522851,121.74464175 29.935520399,121.7448111 29.935538427,121.744855399 29.935543142)))");
        list.add("MULTIPOLYGON (((121.750109542 29.876564634,121.750121867 29.8767818500001,121.750321045 29.8767405730001,121.750287153 29.876551997,121.750109542 29.876564634)))");
        List<List<String>> lst = new ArrayList<>();
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        list1.add("12");
        list1.add("hello");
        list2.add("24");
        list2.add("katus");
        lst.add(list1);
        lst.add(list2);
        System.out.println(GeometryUtil.wktToGeoJson(list, lst));
    }
}
