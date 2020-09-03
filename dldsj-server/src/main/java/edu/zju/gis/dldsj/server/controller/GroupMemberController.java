package edu.zju.gis.dldsj.server.controller;

import edu.zju.gis.dldsj.server.base.BaseController;
import edu.zju.gis.dldsj.server.common.Page;
import edu.zju.gis.dldsj.server.common.Result;
import edu.zju.gis.dldsj.server.entity.GroupMember;
import edu.zju.gis.dldsj.server.entity.searchPojo.GroupMemberSearchPojo;
import edu.zju.gis.dldsj.server.service.GroupMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Jiarui
 * @date 2020/8/31
 */
@Controller
@RequestMapping("/member")
@Slf4j
public class GroupMemberController extends BaseController<GroupMember, GroupMemberService,String, GroupMemberSearchPojo> {

    /**
     * 显示所有成员
     * @param page
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public Result showAllMembers(Page page) {
        return Result.success().setBody(service.showAllMembers(page));
    }

    /**
     * 按照group显示成员
     * @param group
     * @param page
     */
    @RequestMapping(value = "/group", method = RequestMethod.GET)
    @ResponseBody
    public Result showByGroup(@RequestParam String group,Page page) {
        return Result.success().setBody(service.showByGroup(group,page));
    }

}
