package edu.zju.gis.dldsj.server.interceptor;

import edu.zju.gis.dldsj.server.common.Result;
import edu.zju.gis.dldsj.server.constant.CodeConstants;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public SecurityInterceptor getSecurityInterceptor() {
        return new SecurityInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(getSecurityInterceptor());

        String[] excludePaths = new String[]{
                "/user/login",
                "/user/loginstatus",
                "/user/insert",
                "/user/update",
                "/user/selectbyname/**",
                "/user/check/**",
                "/user/checkbyname/**",
                "/user/statistic",
                "/geodata/select/**",
                "/geodata/allselect",
                "/geodata/bytype1",
                "/geodata/bytype2",
                "/geodata/dis",
                "/geodata/populardata",
                "/geodata/detail/**",
                "/studentpaper/selectnew",
                "/studentpaper/selectnew",
                "/studentpaper/fuzzyname/**",
                "/studentpaper/allselect",
                "/academicpaper/selectnew",
                "/academicpaper/fuzzyname/**",
                "/academicpaper/allselect",
                "/lecture/selectnew",
                "/lecture/fuzzyname/**",
                "/lecture/fuzzynameorder/**",
                "/lecture/allselect",
                "/lecture/allselectorder",
                "/onlinetools/allselect",
                "/onlinetools/fuzzyname/**",
                "/mapservice/allselect",
                "/mapservice/fuzzyname/**",
                "/member/**",
                "/run"
        };

        for (String path: excludePaths){
            addInterceptor.excludePathPatterns(path);
        }

        addInterceptor.addPathPatterns("/**");

    }

    private static class SecurityInterceptor extends HandlerInterceptorAdapter {
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
            HttpSession session = request.getSession();
            session.setAttribute("userId", "test");
            if (session.getAttribute("userId") != null) {
                log.warn("SUCCESS [session:" + session.getId() + ", user:" + session.getAttribute("userId").toString() + "]");
                return true;
            } else {
                log.warn("FAILURE: [session:" + session.getId() + "]");
                Result<String> result = new Result<String>(CodeConstants.VALIDATE_ERROR, "用户登陆状态已过期，请重新登陆。");
                result.setBody("");
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().write(result.toString());
                return false;
            }
        }
    }
}
