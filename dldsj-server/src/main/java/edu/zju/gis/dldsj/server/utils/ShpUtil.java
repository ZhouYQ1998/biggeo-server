package edu.zju.gis.dldsj.server.utils;

import com.vividsolutions.jts.geom.Geometry;
import edu.zju.gis.dldsj.server.utils.fs.FsManipulator;
import edu.zju.gis.dldsj.server.utils.fs.FsManipulatorFactory;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.dbf.DbaseFileHeader;
import org.geotools.data.shapefile.dbf.DbaseFileReader;
import org.geotools.data.shapefile.files.ShpFiles;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Keran Sun (katus)
 * @version 1.0, 2020-11-20
 */
public final class ShpUtil {
    private static FeatureIterator<SimpleFeature> getShpReader(String filepath) {
        FeatureIterator<SimpleFeature> reader = null;
        File file = new File(filepath);
        try {
            ShapefileDataStore shpDataStore = new ShapefileDataStore(file.toURI().toURL());
            shpDataStore.setCharset(StandardCharsets.UTF_8);
            String typeName = shpDataStore.getTypeNames()[0];
            SimpleFeatureSource featureSource = shpDataStore.getFeatureSource(typeName);
            FeatureCollection<SimpleFeatureType, SimpleFeature> collection = featureSource.getFeatures();
            reader = collection.features();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reader;
    }

    public static String[] getFieldNames(String filepath) {
        String[] fieldNames = new String[0];
        DbaseFileReader dbfReader;
        try {
            dbfReader = new DbaseFileReader(new ShpFiles(filepath), false, StandardCharsets.UTF_8);
            DbaseFileHeader header = dbfReader.getHeader();
            int numFields = header.getNumFields();
            String[] fields = new String[numFields];
            for (int i = 0; i < numFields; i++) {
                fields[i] = header.getFieldName(i);
            }
            fieldNames = fields;
            dbfReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fieldNames;
    }

    public static String getCrs(String filepath) {
        filepath = filepath.replace(".shp", "") + ".prj";
        String crs = "None";
        try {
            FsManipulator fsManipulator = FsManipulatorFactory.create();
            crs = fsManipulator.readToText(filepath).get(0).trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return crs;
    }

    public static List<Geometry> getGeometries(String filepath, int number, int offset) {
        List<Geometry> geometries = new ArrayList<>();
        FeatureIterator<SimpleFeature> reader = getShpReader(filepath);
        if (reader != null) {
            int i = 0;
            while (reader.hasNext() && i++ < number) {
                SimpleFeature sf = reader.next();
                if (i <= offset) continue;
                geometries.add((Geometry) sf.getDefaultGeometry());
            }
            reader.close();
        }
        return geometries;
    }

    public static List<String> getGeometriesWkt(String filepath, int number, int offset) {
        List<Geometry> geometries = getGeometries(filepath, number, offset);
        return geometries.stream().map(Geometry::toText).collect(Collectors.toList());
    }

    public static List<Map<String, Object>> getAttributes(String filepath, int number, int offset) {
        List<Map<String, Object>> attributeList = new ArrayList<>();
        FeatureIterator<SimpleFeature> reader = getShpReader(filepath);
        String[] fieldNames = getFieldNames(filepath);
        if (reader != null) {
            int i = 0;
            while (reader.hasNext() && i++ < number + offset) {
                SimpleFeature sf = reader.next();
                if (i <= offset) continue;
                HashMap<String, Object> attributes = new HashMap<>();
                for (String fieldName : fieldNames) {
                    attributes.put(fieldName, sf.getAttribute(fieldName));
                }
                attributeList.add(attributes);
            }
            reader.close();
        }
        return attributeList;
    }
}
