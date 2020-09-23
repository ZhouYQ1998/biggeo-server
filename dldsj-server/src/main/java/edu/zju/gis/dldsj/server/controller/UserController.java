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
     * 插入用户
     * @param user User
     * @return result
     */
    @RequestMapping(value = "/insert", method = RequestMethod.PUT)
    @ResponseBody
    @Override
    public Result<User> insert(@RequestBody User user){
        Result<User> result = new Result<>();
        try {
            // 设置随机ID
            String id = UUID.randomUUID().toString();
            user.setId(id);
            // 执行插入
            userService.insert(user);
            result.setCode(CodeConstants.SUCCESS).setBody(user).setMessage("插入成功");
        } catch (RuntimeException e) {
            result.setCode(CodeConstants.SERVICE_ERROR).setMessage("插入失败：" + e.getMessage());
        }
        return result;
    }

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
            result.setCode(CodeConstants.SUCCESS).setBody(user).setMessage("查询成功");
        } catch (RuntimeException e) {
            result.setCode(CodeConstants.SERVICE_ERROR).setMessage("查询失败：" + e.getMessage());
        }
        return result;
    }

//    /**
//     * 用户登陆
//     *
//     * @param input
//     * @param session
//     * @return
//     */
//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    @ResponseBody
//    public String login(@RequestBody String input, HttpSession session) {
//        Result<String> result = new Result<>();
//        JSONObject jsInput = new JSONObject(input);
//        String name = jsInput.getString("name");
//        String pass = jsInput.getString("pass");
//        User user = userService.getByName(name);
//        if (user == null) {
//            result.setCode(CodeConstants.USER_NOT_EXIST);
//            result.setMessage("该用户名不存在，请注册");
//        } else {
//            if (user.getPassword().equals(pass)) {
//                result.setCode(CodeConstants.SUCCESS);
//                result.setBody(user.getId());
//                session.setAttribute("userId", user.getId());
//                session.setAttribute("userName", user.getName());
//                session.setAttribute("password", user.getPassword());
//                session.setAttribute("role", user.getRole());
//            } else {
//                result.setCode(CodeConstants.USERNAME_OR_PASSWORD_ERROR);
//                result.setMessage("用户名或密码错误");
//            }
//        }
//        return result.toString();
//    }
//
//    /**
//     * 用户登陆状态
//     *
//     * @param session
//     * @return userId
//     */
//    @RequestMapping(value = "/loginstatus", method = RequestMethod.GET)
//    @ResponseBody
//    public String getloginstatus(HttpSession session) {
//        Result<String> result = new Result<>();
//        Object userId = session.getAttribute("userId");
//        if (userId != null)
//            return result.setMessage("用户已登陆。").setCode(CodeConstants.SUCCESS)
//                    .setBody(session.getAttribute("userName").toString()).toString();
//        else
//            return result.setMessage("用户未登陆。").setCode(CodeConstants.VALIDATE_ERROR).toString();
//    }
//
//    /**
//     * 用户注销
//     *
//     * @param session
//     * @return
//     */
//    @RequestMapping(value = "/logout", method = RequestMethod.POST)
//    @ResponseBody
//    public String logout(HttpSession session) {
//        Result<String> result = new Result<>();
//        try {
//            session.removeAttribute("userId");
//            session.removeAttribute("userName");
//            session.removeAttribute("fullName");
//            session.removeAttribute("roleId");
//            result.setCode(CodeConstants.SUCCESS);
//            result.setMessage("用户注销成功。");
//            result.setBody("用户注销成功。");
//            return result.toString();
//        } catch (RuntimeException e) {
//            result.setCode(CodeConstants.SERVICE_ERROR);
//            result.setMessage("用户注销失败。");
//            result.setBody("用户注销失败。");
//            return result.toString();
//        }
//    }
//
//    /***
//     * 用户基础信息更新
//     * @param userName
//     * @param updateInfo
//     * @return
//     */
//    @RequestMapping(value = "/reset/info", method = RequestMethod.POST)
//    @ResponseBody
//    public Result updateInfo(@SessionAttribute("userName") String userName, @RequestBody String updateInfo) {
//        User user = userService.getByName(userName);
//        if (user == null) {
//            return Result.error("更新失败，请重新登陆。");
//        }
//        try {
//            JSONObject info = new JSONObject(updateInfo);
//            user.setName(info.getString("name"));
//            user.setPassword(info.getString("password"));
//            userService.update(user);
//            return Result.success().setMessage("用户信息更新成功。");
//        } catch (RuntimeException e) {
//            return Result.error("用户信息更新失败。");
//        }
//    }
//
//    /***
//     * 用户密码更新
//     * @param userName
//     * @param pwdInfo
//     * @return
//     */
//    @RequestMapping(value = "/reset/pwd", method = RequestMethod.POST)
//    @ResponseBody
//    public Result updatePassword(@SessionAttribute("userName") String userName, @RequestBody String pwdInfo) {
//        User user = userService.getByName(userName);
//        if (user == null) {
//            return Result.error("密码重制失败，请重新登陆。");
//        }
//        try {
//            JSONObject infoJson = new JSONObject(pwdInfo);
//            String oldPwd = infoJson.getString("oldPassWord");
//            if (user.getPassword().equals(oldPwd)) {
//                user.setPassword(infoJson.getString("newPassWord"));
//                userService.update(user);
//                return Result.success().setMessage("密码更新成功。");
//            } else {
//                return Result.error("原始密码错误，请重试。");
//            }
//        } catch (Exception e) {
//            return Result.error("密码重制失败。");
//        }
//    }

}
