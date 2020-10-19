package edu.zju.gis.dldsj.server.controller;

import edu.zju.gis.dldsj.server.dto.CodeModel;
import edu.zju.gis.dldsj.server.dto.ProcessResult;
import edu.zju.gis.dldsj.server.service.RunCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author : Shaojian
 * @date : 20201016
 */

@Controller
@CrossOrigin("*")
public class RunCodeController {

    @Autowired
    public final RunCodeService runCode;

    @Autowired
    public RunCodeController(RunCodeService runCode) {
        this.runCode = runCode;
    }

    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public String xcxx() {
        return "sdfsdfsdf";
    }

    @PostMapping("/run")
    @ResponseBody
    public ProcessResult runCode(@RequestBody CodeModel codeModel) throws Exception {
        return runCode.runCode(codeModel.getType(), codeModel.getCode());
    }
}