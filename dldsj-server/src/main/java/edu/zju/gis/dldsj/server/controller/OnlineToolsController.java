package edu.zju.gis.dldsj.server.controller;

import edu.zju.gis.dldsj.server.base.BaseController;
import edu.zju.gis.dldsj.server.base.BaseFilter;
import edu.zju.gis.dldsj.server.entity.OnlineTool;
import edu.zju.gis.dldsj.server.service.OnlineToolsService;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: zjh
 * @date: 20201012
 */

@Log4j
@Controller
@CrossOrigin
@RequestMapping("onlinetools")
public class OnlineToolsController extends BaseController<OnlineTool, OnlineToolsService, String, BaseFilter<String>> {

}
