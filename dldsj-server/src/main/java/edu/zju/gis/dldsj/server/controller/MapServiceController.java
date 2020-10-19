package edu.zju.gis.dldsj.server.controller;

import com.mysql.cj.xdevapi.FilterParams;
import edu.zju.gis.dldsj.server.base.BaseController;
import edu.zju.gis.dldsj.server.base.BaseFilter;
import edu.zju.gis.dldsj.server.entity.MapService;
import edu.zju.gis.dldsj.server.service.MapServiceService;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: zjh
 * @date: 20201012
 */
@Log4j
@Controller
@CrossOrigin
@RequestMapping("/mapservice")
public class MapServiceController extends BaseController<MapService, MapServiceService, String, BaseFilter<String>> {

}
