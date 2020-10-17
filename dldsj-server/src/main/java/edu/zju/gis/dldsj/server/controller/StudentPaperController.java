package edu.zju.gis.dldsj.server.controller;

import edu.zju.gis.dldsj.server.base.BaseController;
import edu.zju.gis.dldsj.server.base.BaseFilter;
import edu.zju.gis.dldsj.server.common.Result;
import edu.zju.gis.dldsj.server.constant.CodeConstants;
import edu.zju.gis.dldsj.server.entity.StudentPaper;
import edu.zju.gis.dldsj.server.entity.User;
import edu.zju.gis.dldsj.server.service.StudentPaperService;
import edu.zju.gis.dldsj.server.service.UserService;
import edu.zju.gis.dldsj.server.utils.EmailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zyq 2020/09/23
 */
@Slf4j
@Controller
@CrossOrigin
@RequestMapping("/studentpaper")
public class StudentPaperController extends BaseController<StudentPaper, StudentPaperService, String, BaseFilter<String>> {

    @Autowired
    private StudentPaperService studentPaperService;

}
