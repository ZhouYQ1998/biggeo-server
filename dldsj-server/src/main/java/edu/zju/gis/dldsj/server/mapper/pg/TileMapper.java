package edu.zju.gis.dldsj.server.mapper.pg;

import edu.zju.gis.dldsj.server.entity.MvtTile;

/**
 * @author Keran Sun (katus)
 * @version 1.0, 2020-11-23
 */
public interface TileMapper {
    MvtTile getTile(String tableName, String layerName, String geomName, Double lonMin, Double latMin, Double lonMax, Double latMax);
}
