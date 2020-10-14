package edu.zju.gis.dldsj.server.service.impl;

import edu.zju.gis.dldsj.server.base.BaseServiceImpl;
import edu.zju.gis.dldsj.server.entity.teachModel;
import edu.zju.gis.dldsj.server.mapper.teachModelMapper;
import edu.zju.gis.dldsj.server.service.teachModelService;
import org.springframework.stereotype.Service;

/**
 * @author Jiarui
 * @date 2020/10/13
 */

@Service
public class teachModelServiceImpl extends BaseServiceImpl<teachModelMapper, teachModel,String> implements teachModelService {
}
