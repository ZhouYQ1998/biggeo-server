package edu.zju.gis.dldsj.server.controller;

import edu.zju.gis.dldsj.server.base.BaseController;
import edu.zju.gis.dldsj.server.common.Page;
import edu.zju.gis.dldsj.server.common.Result;
import edu.zju.gis.dldsj.server.constant.CodeConstants;
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
 * @author Jiarui 2020/08/31
 * @update zyq 2020/09/25
 */
@Controller
@RequestMapping("/member")
@Slf4j
public class GroupMemberController extends BaseController<GroupMember, GroupMemberService, String, GroupMemberSearchPojo> {

    /**
     * 查询所有开发者
     * @param page Page
     */
    @RequestMapping(value = "/showall", method = RequestMethod.GET)
    @ResponseBody
    public Result<Page<GroupMember>> showAllMembers(Page<GroupMember> page) {
        return new Result<Page<GroupMember>>().setCode(CodeConstants.SUCCESS).setBody(service.showAllMembers(page));
    }

    /**
     * 查询指导老师
     * @param page Page
     */
    @RequestMapping(value = "/showteachers", method = RequestMethod.GET)
    @ResponseBody
    public Result<Page<GroupMember>> showTeachers(Page<GroupMember> page) {
        return new Result<Page<GroupMember>>().setCode(CodeConstants.SUCCESS).setBody(service.showTeachers(page));
    }

    /**
     * 查询开发者（通过版本）
     * @param page Page
     * @param version String
     */
    @RequestMapping(value = "/showbyversion", method = RequestMethod.GET)
    @ResponseBody
    public Result<Page<GroupMember>> showTeachers(@RequestParam String version, Page<GroupMember> page) {
        return new Result<Page<GroupMember>>().setCode(CodeConstants.SUCCESS).setBody(service.showByVersion(version, page));
    }

    /**
     * 查询开发者（通过版本和组号）
     * @param team String
     * @param page Page
     */
    @RequestMapping(value = "/showbyteam", method = RequestMethod.GET)
    @ResponseBody
    public Result<Page<GroupMember>> showByTeam(@RequestParam String version, @RequestParam String team, Page<GroupMember> page) {
        return new Result<Page<GroupMember>>().setCode(CodeConstants.SUCCESS).setBody(service.showByTeam(version, team, page));
    }

}
