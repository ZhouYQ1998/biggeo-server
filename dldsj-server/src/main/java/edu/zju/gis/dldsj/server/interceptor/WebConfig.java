package edu.zju.gis.dldsj.server.interceptor;

import edu.zju.gis.dldsj.server.common.Result;
import edu.zju.gis.dldsj.server.constant.CodeConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 用于进行用户登录状态验证
 *
 * @author yanlo yanlong_lee@qq.com
 * @version 1.0 2018/08/15
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final static String SESSION_KEY = "userName";

    @Bean
    public SecurityInterceptor getSecurityInterceptor() {
        return new SecurityInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(getSecurityInterceptor());

        addInterceptor.excludePathPatterns("/error");
        addInterceptor.excludePathPatterns("/user/login**");
        addInterceptor.excludePathPatterns("/user/register");
        addInterceptor.addPathPatterns("/**");
    }

    private static class SecurityInterceptor extends HandlerInterceptorAdapter {
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
            HttpSession session = request.getSession();
            //手动添加用户信息，便于调试。
            session.setAttribute(SESSION_KEY, "ubt");
            session.setAttribute("userId", "1");
            if (session.getAttribute("userId") != null) {
                return true;
            } else {
                Result<String> result =
                        new Result<String>(CodeConstants.VALIDATE_ERROR, "用户登陆状态已过期，请重新登陆。");
                result.setBody("");
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().write(result.toString());
                return false;
            }
//            return true;
        }
    }
}