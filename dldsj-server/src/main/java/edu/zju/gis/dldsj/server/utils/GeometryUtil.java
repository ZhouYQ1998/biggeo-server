package edu.zju.gis.dldsj.server.utils;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import org.geotools.geojson.geom.GeometryJSON;
import org.geotools.geometry.jts.JTS;
import org.geotools.geometry.jts.ReferencedEnvelope;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

/**
 * @author Keran Sun (katus)
 * @version 1.0, 2020-11-09
 */
public final class GeometryUtil {
    public static final String EMPTY_GeoJSON = "{\"type\":\"FeatureCollection\",\"features\":[]}";

    /**
     * 将wkt字符串List转化为GeoJson格式字符串
     * @param wktList wkt字符串List
     * @return GeoJson格式字符串
     * @throws IOException io
     * @throws ParseException WKT解析异常
     */
    public static String wktToGeoJson(List<String> wktList, List<List<String>> propList) throws IOException, ParseException {
        if (wktList == null || wktList.isEmpty())
            return EMPTY_GeoJSON;
        WKTReader wktReader = new WKTReader();
        StringWriter writer = new StringWriter();
        GeometryJSON geometryJSON = new GeometryJSON(20);
        Geometry geometry = wktReader.read(wktList.get(0));
        writer.append(String.format("{\"type\": \"Feature\",\"properties\": {%s},\"geometry\":", getPropJson(propList.get(0))));
        geometryJSON.write(geometry, writer);
        writer.append("}");
        for (int i = 1; i < wktList.size(); i++) {
            writer.append(String.format(", {\"type\": \"Feature\",\"properties\": {%s},\"geometry\":", getPropJson(propList.get(i))));
            geometry = wktReader.read(wktList.get(i));
            geometryJSON.write(geometry, writer);
            writer.append("}");
        }
        return String.format("{\"type\":\"FeatureCollection\",\"features\":[%s]}", writer.toString());
    }

    private static String getPropJson(List<String> prop) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < prop.size(); i++) {
            builder.append("\"COL_").append(i).append("\": \"").append(prop.get(i)).append("\",");
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

    /**
     * 根据wkt字符串获取几何类型
     * @param wkt wkt字符串
     * @return 几何类型
     */
    public static String getGeomTypeByWkt(String wkt) {
        WKTReader wktReader = new WKTReader();
        try {
            Geometry geometry = wktReader.read(wkt);
            return geometry.getGeometryType();
        } catch (ParseException e) {
            return "Unknown";
        }
    }

    /**
     * 获取一系列几何要素的四至
     * @param wktList 几何的wkt列表
     * @param bbox 四至结果
     * @throws ParseException wkt解析异常
     */
    public static void getBboxOfWkt(List<String> wktList, Double[] bbox) throws ParseException {
        if (wktList == null || wktList.isEmpty())
            return;
        WKTReader wktReader = new WKTReader();
        Geometry geometry = wktReader.read(wktList.get(0));
        ReferencedEnvelope envelope = JTS.toEnvelope(geometry);
        double x1 = envelope.getMinX(), y1 = envelope.getMinY(), x2 = envelope.getMaxX(), y2 = envelope.getMaxY();
        for (int i = 1; i < wktList.size(); i++) {
            geometry = wktReader.read(wktList.get(i));
            envelope = JTS.toEnvelope(geometry);
            x1 = Math.min(x1, envelope.getMinX());
            y1 = Math.min(y1, envelope.getMinY());
            x2 = Math.max(x2, envelope.getMaxX());
            y2 = Math.max(y2, envelope.getMaxY());
        }
        bbox[0] = x1;
        bbox[1] = y1;
        bbox[2] = x2;
        bbox[3] = y2;
    }
}
