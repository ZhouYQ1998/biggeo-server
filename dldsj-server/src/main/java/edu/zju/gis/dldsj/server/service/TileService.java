package edu.zju.gis.dldsj.server.service;

import edu.zju.gis.dldsj.server.entity.MvtTile;

/**
 * @author Keran Sun (katus)
 * @version 1.0, 2020-11-23
 */
public interface TileService {
    MvtTile getTile(String tableName, String layerName, String geomName, Integer z, Integer x, Integer y);
}
