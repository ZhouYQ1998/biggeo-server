package edu.zju.gis.dldsj.server.utils;

import com.vividsolutions.jts.io.ParseException;
import org.geotools.geojson.geom.GeometryJSON;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.WKTReader;
//import org.locationtech.jts.io.WKTReader;
//import org.locationtech.jts.geom.Geometry;


/**
 * @author Jiarui
 * @date 2020/10/28
 */
public class dataProcessUtil {

    // Geometry 转 GeoJSON 的坐标精度
    private static final int GEOMETRY_JSON_DECIMAL = 9;

    public static String wkt2GeoJson(String wktString) throws ParseException {
        WKTReader wktReader = new WKTReader();
        Geometry geometry = wktReader.read(wktString);
        GeometryJSON geometryJSON = new GeometryJSON(GEOMETRY_JSON_DECIMAL);

        return geometryJSON.toString(geometry);

    }

    public void main(String[] args) throws ParseException {
        
        String wktString = "MULTIPOLYGON (((121.771387057383 29.9380971493358,121.793727312699 29.9446520745484,121.787707483423 29.9290005184285,121.761621556556 29.9232482371194,121.771387057383 29.9380971493358)))";
        String geoJson = this.wkt2GeoJson(wktString);
        System.out.println(geoJson);
    }

}
