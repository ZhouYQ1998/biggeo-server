package edu.zju.gis.dldsj.server.service.impl;

import edu.zju.gis.dldsj.server.entity.MvtTile;
import edu.zju.gis.dldsj.server.mapper.pg.TileMapper;
import edu.zju.gis.dldsj.server.service.TileService;
import edu.zju.gis.dldsj.server.utils.TileUtil;
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
        Double xMin = TileUtil.tile2x(x, z);
        Double yMin = TileUtil.tile2y(y, z);
        Double xMax = TileUtil.tile2x(x+1, z);
        Double yMax = TileUtil.tile2y(y+1, z);
        layerName = "'" + layerName + "'";
        return tileMapper.getTile(tableName, layerName, geomName, xMin, yMin, xMax, yMax);
    }
}
