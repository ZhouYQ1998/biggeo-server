package edu.zju.gis.dldsj.server.service.impl;

import edu.zju.gis.dldsj.server.base.BaseServiceImpl;
import edu.zju.gis.dldsj.server.entity.TeachModel;
import edu.zju.gis.dldsj.server.mapper.TeachModelMapper;
import edu.zju.gis.dldsj.server.service.TeachModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author Jiarui
 * @date 2020/10/13
 */

@Service
public class TeachModelServiceImpl extends BaseServiceImpl<TeachModelMapper, TeachModel,String> implements TeachModelService {
    @Autowired
    TeachModelMapper teachModelmapper;


    public int insertwll(TeachModel teachModel){
        return teachModelmapper.insertwll(teachModel);
    }

}
