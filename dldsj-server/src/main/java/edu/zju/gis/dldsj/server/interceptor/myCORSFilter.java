package edu.zju.gis.dldsj.server.interceptor;

import edu.zju.gis.dldsj.server.config.CommonSetting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
@Slf4j
@WebFilter(urlPatterns = "/*", filterName = "mycorsfilter")
public class myCORSFilter implements Filter {
    @Autowired
    private CommonSetting setting;

    //    public class myCORSFilter  {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        List<String> whiteList = setting.getFrontEndRegionList();
        log.warn("WHITE: " + whiteList.toString());
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String myOrigin = request.getHeader("Origin");
        log.warn("REQUEST ORIGIN: " + myOrigin);
        boolean isValid = whiteList.contains(myOrigin);
        response.setHeader("Access-Control-Allow-Origin", isValid ? myOrigin : "*");
        log.warn("SET ORIGIN: " + (isValid ? myOrigin : "*"));
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
        response.setHeader("Access-Control-Max-Age", setting.getSessionMaxAge());
        response.setHeader("Access-Control-Allow-Headers", " Origin, X-Requested-With, Content-Type, Accept, Authorization");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}