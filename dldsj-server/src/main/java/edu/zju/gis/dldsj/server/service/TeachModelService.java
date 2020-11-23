package edu.zju.gis.dldsj.server.service;

import edu.zju.gis.dldsj.server.base.BaseService;
import edu.zju.gis.dldsj.server.entity.TeachModel;

/**
 * @author Jiarui
 * @date 2020/10/13
 */
public interface TeachModelService extends BaseService<TeachModel,String> {
    int insertwll(TeachModel teachModel);
}
