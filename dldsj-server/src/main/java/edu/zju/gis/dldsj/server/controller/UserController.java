package edu.zju.gis.dldsj.server.controller;

import edu.zju.gis.dldsj.server.common.Page;
import edu.zju.gis.dldsj.server.common.Result;
import edu.zju.gis.dldsj.server.config.CommonSetting;
import edu.zju.gis.dldsj.server.constant.CodeConstants;
import edu.zju.gis.dldsj.server.constant.FunctionStatus;
import edu.zju.gis.dldsj.server.entity.*;
import edu.zju.gis.dldsj.server.service.*;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.time.Instant;
import java.util.*;

/**
 * @author yanlo yanlong_lee@qq.com
 * @version 1.0 2018/08/09
 */
@Slf4j
//@CrossOrigin
@Controller
@RequestMapping("/user")
public class UserController {
    private final int UserSpace_LIMIT = 1073741824;
    @Autowired
    private CommonSetting setting;
    @Autowired
    private UserService userService;
    @Value("${spring.mvc.static-path-pattern}")
    private String staticPathPattern;

    /**
     * 用户注册
     *
     * @param requestBody
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public String register(@RequestBody String requestBody) {
        Result<String> result = new Result<>();
        try {
            JSONObject json = new JSONObject(requestBody);
            User user = new User();
            String id = UUID.randomUUID().toString();
            user.setId(id);
            user.setRoleId(json.getString("roleId"));
            user.setName(json.getString("name"));
            user.setFullName(json.optString("fullName"));
            user.setPassword(json.getString("password"));
            user.setPsdPrompt(json.optString("psd_prompt"));
            user.setSex(json.getString("sex"));
            user.setDescription(json.optString("description"));
            user.setPhone(json.optString("phone"));
            user.setEmail(json.optString("email"));
            user.setOrganization(json.optString("organization"));
            user.setDepartment(json.optString("department"));
            user.setRegistrationTime(Date.from(Instant.now()));
            user.setStatus(FunctionStatus.NORMAL.name());

            user.setPrivateDir("/" + json.getString("name") + id);
            user.setDirSize(UserSpace_LIMIT);
            String sourceUrl = json.optString("avatarPath");
            String picUrl = "";
            if (!sourceUrl.isEmpty()) {
                picUrl = staticPathPattern.replace("*", "") + "defaultavatar.jpg";
            } else {
                picUrl = staticPathPattern.replace("*", "") + "defaultavatar.jpg";
            }
            user.setAvatarPath(picUrl);
            userService.insert(user);
            result.setCode(CodeConstants.SUCCESS).setBody("success");
        } catch (RuntimeException e) {
            log.error("用户添加失败", e);
            result.setCode(CodeConstants.VALIDATE_ERROR).setMessage("用户注册失败：" + e.getMessage());
        }
        return result.toString();
    }

    /**
     * 用户登陆
     *
     * @param input
     * @param session
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public String login(@RequestBody String input, HttpSession session) {
        Result<String> result = new Result<>();
        JSONObject jsInput = new JSONObject(input);
        String name = jsInput.getString("name");
        String pass = jsInput.getString("pass");
        User user = userService.getByName(name);
        if (user == null) {
            result.setCode(CodeConstants.USER_NOT_EXIST);
            result.setMessage("该用户名不存在，请注册");
        } else {
            if (user.getPassword().equals(pass)) {
                result.setCode(CodeConstants.SUCCESS);
                result.setBody(user.getId());
                session.setAttribute("userId", user.getId());
                session.setAttribute("userName", user.getName());
                session.setAttribute("fullName", user.getFullName());
                session.setAttribute("roleId", user.getRoleId());
            } else {
                result.setCode(CodeConstants.USERNAME_OR_PASSWORD_ERROR);
                result.setMessage("用户名或密码错误");
            }
        }
        System.out.println(result.toString());
        return result.toString();
    }

    /**
     * 判断用户名是否存在
     *
     * @param userName
     * @return
     */
    @RequestMapping(value = "/ifexist/{userName}", method = RequestMethod.GET)
    @ResponseBody
    public String ifUserNameExist(@PathVariable  String userName) {
        Result<String> result = new Result<>();
        try {
            if (userService.ifUserNameExist(userName))
                result.setCode(CodeConstants.SUCCESS).setMessage("用户名已存在。").setBody("0");
            else
                result.setCode(CodeConstants.SUCCESS).setMessage("用户名可用。").setBody("1");
        } catch (RuntimeException e) {
            result.setCode(CodeConstants.SERVICE_ERROR).setMessage("检测失败：" + e.getMessage());
        }
        return result.toString();
    }

    /**
     * 用户登陆状态
     *
     * @param session
     * @return userId
     */
    @RequestMapping(value = "/loginstatus", method = RequestMethod.GET)
    @ResponseBody
    public String getloginstatus(HttpSession session) {
        Result<String> result = new Result<>();
        Object userId = session.getAttribute("userId");
        if (userId != null)
            return result.setMessage("用户已登陆。").setCode(CodeConstants.SUCCESS)
                    .setBody(session.getAttribute("userName").toString()).toString();
        else
            return result.setMessage("用户未登陆。").setCode(CodeConstants.VALIDATE_ERROR).toString();
    }

    /**
     * 用户注销
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public String logout(HttpSession session) {
        Result<String> result = new Result<>();
        try {
            session.removeAttribute("userId");
            session.removeAttribute("userName");
            session.removeAttribute("fullName");
            session.removeAttribute("roleId");
            result.setCode(CodeConstants.SUCCESS);
            result.setMessage("用户注销成功。");
            result.setBody("用户注销成功。");
            return result.toString();
        } catch (RuntimeException e) {
            result.setCode(CodeConstants.SERVICE_ERROR);
            result.setMessage("用户注销失败。");
            result.setBody("用户注销失败。");
            return result.toString();
        }
    }

    /**
     * 获取用户信息
     *
     * @param userName
     * @return
     */
    @RequestMapping(value = "/getinfo/{userName}", method = RequestMethod.GET)
    @ResponseBody
    public String getUserInfo(@SessionAttribute("userName") String userName) {
        Result<User> result = new Result<>();
        try {
            User body = userService.getByName(userName);
            result.setCode(CodeConstants.SUCCESS).setMessage("获取用户信息成功。").setBody(body);
        } catch (RuntimeException e) {
            result.setCode(CodeConstants.SERVICE_ERROR).setMessage("获取用户信息失败：" + e.getMessage());
        }
        return result.toString();
    }

    /***
     * 用户基础信息更新
     * @param userName
     * @param updateInfo
     * @return
     */
    @RequestMapping(value = "/reset/info", method = RequestMethod.POST)
    @ResponseBody
    public Result updateInfo(@SessionAttribute("userName") String userName, @RequestBody String updateInfo) {
        User user = userService.getByName(userName);
        if (user == null) {
            return Result.error("更新失败，请重新登陆。");
        }
        try {
            JSONObject info = new JSONObject(updateInfo);
            user.setFullName(info.getString("full_name"));
            user.setSex(info.getString("sex"));
            user.setDescription(info.getString("description"));
            user.setPhone(info.getString("phone"));
            user.setEmail(info.getString("email"));
            user.setOrganization(info.getString("organization"));
            user.setDepartment(info.getString("department"));
            userService.update(user);
            return Result.success().setMessage("用户信息更新成功。");
        } catch (RuntimeException e) {
            return Result.error("用户信息更新失败。");
        }
    }

    /***
     * 用户密码更新
     * @param userName
     * @param pwdInfo
     * @return
     */
    @RequestMapping(value = "/reset/pwd", method = RequestMethod.POST)
    @ResponseBody
    public Result updatePassword(@SessionAttribute("userName") String userName, @RequestBody String pwdInfo) {
        User user = userService.getByName(userName);
        if (user == null) {
            return Result.error("密码重制失败，请重新登陆。");
        }
        try {
            JSONObject infoJson = new JSONObject(pwdInfo);
            String oldPwd = infoJson.getString("oldPassWord");
            if (user.getPassword().equals(oldPwd)) {
                user.setPassword(infoJson.getString("newPassWord"));
                userService.update(user);
                return Result.success().setMessage("密码更新成功。");
            } else {
                return Result.error("原始密码错误，请重试。");
            }
        } catch (Exception e) {
            return Result.error("密码重制失败。");
        }
    }

}
