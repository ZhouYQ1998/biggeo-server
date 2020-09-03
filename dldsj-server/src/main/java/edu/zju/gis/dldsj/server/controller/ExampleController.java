package edu.zju.gis.dldsj.server.controller;

import edu.zju.gis.dldsj.server.base.BaseController;
import edu.zju.gis.dldsj.server.common.Result;
import edu.zju.gis.dldsj.server.entity.Example;
import edu.zju.gis.dldsj.server.entity.searchPojo.ExampleSearchPojo;
import edu.zju.gis.dldsj.server.service.ExampleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Hu
 * @date 2020/8/23
 **/
@Controller
@RequestMapping("/example")
@Slf4j
public class ExampleController extends BaseController<Example, ExampleService, String, ExampleSearchPojo> {

  @RequestMapping(value = "/test", method = RequestMethod.GET)
  @ResponseBody
  public Result getTest() {
    String test = "success";
    Result.error().setMessage("message").setCode(404);
    return Result.success().setBody(test);
  }


}
