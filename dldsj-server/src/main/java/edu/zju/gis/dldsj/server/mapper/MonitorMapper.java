package edu.zju.gis.dldsj.server.mapper;

import edu.zju.gis.dldsj.server.entity.Monitor;

/**
 * @author Keran Sun (katus)
 * @version 1.0, 2020-10-07
 */
public interface MonitorMapper {
    int insertSelective(Monitor monitor);

    int updateByPrimaryKeySelective(Monitor monitor);

    Monitor selectByName(String name);

    int updateByName(Monitor monitor);
}
