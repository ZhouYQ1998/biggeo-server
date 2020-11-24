package edu.zju.gis.dldsj.server.service.impl;

import edu.zju.gis.dldsj.server.entity.MvtTile;
import edu.zju.gis.dldsj.server.mapper.pg.TileMapper;
import edu.zju.gis.dldsj.server.service.TileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Keran Sun (katus)
 * @version 1.0, 2020-11-23
 */
@Service("tileService")
public class TileServiceImpl implements TileService {
    @Autowired
    TileMapper tileMapper;

    @Override
    public MvtTile getTile(String tableName, String layerName, String geomName, Integer z, Integer x, Integer y) {
        Double lonMin = tile2Lon(x, z);
        Double latMin = tile2Lat(y, z);
        Double lonMax = tile2Lon(x+1, z);
        Double latMax = tile2Lat(y+1, z);
        layerName = "'" + layerName + "'";
        return tileMapper.getTile(tableName, layerName, geomName, lonMin, latMin, lonMax, latMax);
    }

    private static double tile2Lat(int y, int z) {
        int n = 1 << z;
//        lat_rad = math.atan(math.sinh(math.pi * (1 - 2 * ytile / n)))
//        lat_deg = math.degrees(lat_rad)
//        return 1.0 * y / n * 180 - 90;
        return Math.toDegrees(Math.atan(Math.sinh(Math.PI * (1 - 2.0*y / n))));
    }

    private static double tile2Lon(int x, int z) {
        int n = 1 << z;
        return 1.0 * x / n * 360 - 180;
    }
}
