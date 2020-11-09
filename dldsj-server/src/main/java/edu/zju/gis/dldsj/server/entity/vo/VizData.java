package edu.zju.gis.dldsj.server.entity.vo;

import edu.zju.gis.dldsj.server.utils.GeometryUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Keran Sun (katus)
 * @version 1.0, 2020-11-09
 */
@Getter
@Setter
public class VizData {
    private String geomType;
    private String geomData;
    private String structuredData;
    private Double[] bbox;

    public VizData(String geomType, String geomData) {
        this(geomType, geomData, "{\"table\": []}");
    }

    public VizData(String structuredData) {
        this("Unknown", GeometryUtil.EMPTY_GeoJSON, structuredData);
    }

    public VizData(String geomType, String geomData, String structuredData) {
        this.geomType = geomType;
        this.geomData = geomData;
        this.structuredData = structuredData;
        this.bbox = new Double[4];
    }
}
