package edu.zju.gis.dldsj.server.controller;

import edu.zju.gis.dldsj.server.base.BaseController;
import edu.zju.gis.dldsj.server.base.BaseFilter;
import edu.zju.gis.dldsj.server.common.Result;
import edu.zju.gis.dldsj.server.constant.CodeConstants;
import edu.zju.gis.dldsj.server.constant.EmailConstants;
import edu.zju.gis.dldsj.server.entity.*;
import edu.zju.gis.dldsj.server.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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
    public Result<String> deleteUserByName(@PathVariable String name) {
        return userService.deleteByName(name);
    }

    /***
     * 查询用户（通过NAME，非主键）
     * @param name String
     * @return result
     */
    @RequestMapping(value = "/selectbyname/{name}", method = RequestMethod.GET)
    @ResponseBody
    public Result<User> selectUserByName(@PathVariable String name) {
        return userService.selectByName(name);
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
        User user = userService.selectByName(requestUser.getName()).getBody();
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
        User user = userService.select(userId).getBody();
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

    /**
     * 发送验证码
     * @param email String
     * @return result Result
     */
    @RequestMapping(value = "/check/{email}", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String, String>> check(@PathVariable String email) {
        Result<Map<String, String>> result = new Result<>();

        Map<String, String> map = new HashMap<>();
        map.put("email", email);

        StringBuilder codeBuilder = new StringBuilder();
        for(int i=0; i<6; i++){
            codeBuilder.append(String.valueOf((int)Math.floor(Math.random()*10)));
        }
        String code = codeBuilder.toString();
        map.put("code", code);

        result.setBody(map);

        try{
            Properties props = new Properties();
            props.setProperty("mail.transport.protocol", "smtp");
            props.setProperty("mail.smtp.host", EmailConstants.EmailSMTPHost);
            props.setProperty("mail.smtp.auth", "true");

            Authenticator auth = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(EmailConstants.EmailAccount, EmailConstants.EmailPassword);
                }
            };

            Session session = Session.getInstance(props, auth);
            session.setDebug(true);

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EmailConstants.EmailAccount, "地学大数据教学平台", "UTF-8"));
            message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(email));
            message.setSubject("注册验证码", "UTF-8");
            message.setContent(EmailConstants.EmailContent(code), "text/html;charset=UTF-8");
            message.setSentDate(new Date());

            Transport transport = session.getTransport("smtp");
            transport.connect(EmailConstants.EmailSMTPHost, EmailConstants.EmailAccount, EmailConstants.EmailPassword);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

            result.setCode(CodeConstants.SUCCESS).setMessage("验证码发送成功");

        }catch(Exception e){
            result.setCode(CodeConstants.SERVICE_ERROR).setMessage("验证码发送失败：" + e.getMessage());
        }

        return result;
    }
}
