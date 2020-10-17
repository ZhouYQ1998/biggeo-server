package edu.zju.gis.dldsj.server.service.impl;

import edu.zju.gis.dldsj.server.entity.Monitor;
import edu.zju.gis.dldsj.server.mapper.MonitorMapper;
import edu.zju.gis.dldsj.server.service.MonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Keran Sun (katus)
 * @version 1.0, 2020-10-07
 */
@Service
public class MonitorServiceImpl implements MonitorService {
    @Autowired
    private MonitorMapper monitorMapper;

    @Override
    public int insert(Monitor monitor) {
        return monitorMapper.insertSelective(monitor);
    }

    @Override
    public void update(Monitor monitor) {
        monitorMapper.updateByPrimaryKeySelective(monitor);
    }

    @Override
    public Monitor getByName(String name) {
        return monitorMapper.selectByName(name);
    }

    @Override
    public int updateByName(Monitor monitor) {
        return monitorMapper.updateByNameSelective(monitor);
    }
}
