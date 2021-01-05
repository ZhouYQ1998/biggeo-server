package edu.zju.gis.dldsj.server.mapper.pg;

import edu.zju.gis.dldsj.server.entity.MvtTile;
import org.apache.ibatis.annotations.Param;

/**
 * @author Keran Sun (katus)
 * @version 1.0, 2020-11-23
 */
public interface TileMapper {
    MvtTile getTile(@Param("tableName") String tableName, @Param("layerName") String layerName, @Param("geomName") String geomName,
                    @Param("xMin") Double xMin, @Param("yMin") Double yMin, @Param("xMax") Double xMax, @Param("yMax") Double yMax);

    MvtTile getTile2(@Param("tableName") String tableName, @Param("layerName") String layerName, @Param("geomName") String geomName,
                    @Param("xMin") Double xMin, @Param("yMin") Double yMin, @Param("xMax") Double xMax, @Param("yMax") Double yMax);

    String getOneWkt(@Param("tableName") String tableName, @Param("geomName") String geomName);
}
