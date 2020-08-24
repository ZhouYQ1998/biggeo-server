package edu.zju.gis.dldsj.server.service.impl;

import edu.zju.gis.dldsj.server.base.BaseServiceImpl;
import edu.zju.gis.dldsj.server.entity.Example;
import edu.zju.gis.dldsj.server.mapper.ExampleMapper;
import edu.zju.gis.dldsj.server.service.ExampleService;
import org.springframework.stereotype.Service;

/**
 * @author Hu
 * @date 2020/8/23
 **/
@Service
public class ExampleServiceImpl extends BaseServiceImpl<ExampleMapper, Example, String> implements ExampleService {
}
