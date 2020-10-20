package edu.zju.gis.dldsj.server.service;

import edu.zju.gis.dldsj.server.base.BaseService;
import edu.zju.gis.dldsj.server.entity.TeachModel;
import edu.zju.gis.dldsj.server.mapper.TeachModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @author Jiarui
 * @date 2020/10/13
 */
public interface TeachModelService extends BaseService<TeachModel,String> {
    int insertwll(TeachModel teachModel);
}
