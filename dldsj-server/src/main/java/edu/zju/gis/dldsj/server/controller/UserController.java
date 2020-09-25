package edu.zju.gis.dldsj.server.controller;

import edu.zju.gis.dldsj.server.base.BaseController;
import edu.zju.gis.dldsj.server.base.BaseFilter;
import edu.zju.gis.dldsj.server.common.Result;
import edu.zju.gis.dldsj.server.constant.CodeConstants;
import edu.zju.gis.dldsj.server.entity.*;
import edu.zju.gis.dldsj.server.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author zyq 2020/09/23
 */
@Slf4j
@Controller
@RequestMapping("/user")
public class UserController extends BaseController<User, UserService, String, BaseFilter<String>> {

    @Autowired
    private UserService userService;

    /***
     * 删除用户（通过NAME，非主键）
     * @param name String
     * @return result
     */
    @RequestMapping(value = "/deletebyname/{name}", method = RequestMethod.DELETE)
    @ResponseBody
    public Result<User> deleteUserByName(@PathVariable String name) {
        Result<User> result = new Result<>();
        try {
            userService.deleteByName(name);
            result.setCode(CodeConstants.SUCCESS).setMessage("删除成功");
        } catch (RuntimeException e) {
            result.setCode(CodeConstants.SERVICE_ERROR).setMessage("删除失败：" + e.getMessage());
        }
        return result;
    }

    /***
     * 查询用户（通过NAME，非主键）
     * @param name String
     * @return result
     */
    @RequestMapping(value = "/selectbyname/{name}", method = RequestMethod.GET)
    @ResponseBody
    public Result<User> selectUserByName(@PathVariable String name) {
        Result<User> result = new Result<>();
        try {
            User user = userService.selectByName(name);
            if(user != null){
                result.setCode(CodeConstants.SUCCESS).setBody(user).setMessage("查询成功");
            }
            else{
                result.setCode(CodeConstants.VALIDATE_ERROR).setMessage("查询失败：无结果");
            }
        } catch (RuntimeException e) {
            result.setCode(CodeConstants.SERVICE_ERROR).setMessage("查询失败：" + e.getMessage());
        }
        return result;
    }

    /***
     * 用户登录
     * @param requestUser User
     * @param session HttpSession
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Result<User> login(@RequestBody User requestUser, HttpSession session){
        Result<User> result = new Result<>();
        User user = userService.selectByName(requestUser.getName());
        if(user == null){
            result.setCode(CodeConstants.USER_NOT_EXIST).setMessage("登录失败：用户不存在");
            return result;
        }
        if(requestUser.getPassword().equals(user.getPassword())){
            session.setAttribute("userId", user.getId());
            session.setAttribute("userName", user.getName());
            session.setAttribute("password", user.getPassword());
            session.setAttribute("phone", user.getPhone());
            session.setAttribute("email", user.getEmail());
            session.setAttribute("role", user.getRole());
            result.setCode(CodeConstants.SUCCESS).setBody(user).setMessage("登录成功");
        }
        else{
            result.setCode(CodeConstants.USERNAME_OR_PASSWORD_ERROR).setMessage("登录失败：用户名或密码错误");
        }
        return result;
    }

    /**
     * 用户登录状态
     * @param session HttpSession
     * @return result Result
     */
    @RequestMapping(value = "/loginstatus", method = RequestMethod.GET)
    @ResponseBody
    public Result<User> getLoginStatus(HttpSession session) {
        Result<User> result = new Result<>();
        String userId = (String)session.getAttribute("userId");
        if (userId == null){
            result.setCode(CodeConstants.VALIDATE_ERROR).setMessage("用户未登录");
            return result;
        }
        User user = userService.select(userId);
        result.setCode(CodeConstants.SUCCESS).setBody(user).setMessage("用户已登录");
        return result;
    }

    /**
     * 用户注销
     * @param session HttpSession
     * @return result Result
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public Result<String> logout(HttpSession session) {
        Result<String> result = new Result<>();
        try {
            String userId = (String)session.getAttribute("userId");
            session.removeAttribute("userId");
            session.removeAttribute("userName");
            session.removeAttribute("password");
            session.removeAttribute("phone");
            session.removeAttribute("email");
            session.removeAttribute("role");
            result.setCode(CodeConstants.SUCCESS).setBody(userId).setMessage("用户注销成功");
        } catch (RuntimeException e) {
            result.setCode(CodeConstants.SERVICE_ERROR).setMessage("用户注销失败");
        }
        return result;
    }

}
