package edu.zju.gis.dldsj.server.service;

import edu.zju.gis.dldsj.server.entity.Monitor;

/**
 * @author Keran Sun (katus)
 * @version 1.0, 2020-10-07
 */
public interface MonitorService {
    int insert(Monitor monitor);

    void update(Monitor monitor);

    Monitor getByName(String name);

    int updateByName(Monitor monitor);
}
