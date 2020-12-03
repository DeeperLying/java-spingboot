package com.evan.wj.Interceptor;

import com.evan.wj.pojo.Audience;
import com.evan.wj.result.Result;
import com.evan.wj.utils.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.common.Nullable;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.Map;

public class CustomInterceptor implements HandlerInterceptor {

    @Autowired
    private Audience audience;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception {
        /*final String authHeader = request.getHeader(JwtTokenUtil.AUTH_HEADER_KEY);
        System.out.println(authHeader);*/
        String url = request.getRequestURI();
        if (url.equals("/api/login")) {
            // 登陆不做拦截
            return true;
        }
        final String authHeader = request.getHeader(JwtTokenUtil.AUTH_HEADER_KEY);

        if (StringUtils.isBlank(authHeader) || !authHeader.startsWith(JwtTokenUtil.TOKEN_PREFIX)) {
            System.out.println("### 用户未登录，请先登录 ###");
            errorResponse(response);
            return false;
        }

        // 获取token
        final String token = authHeader.substring(7);

        // 手动初始化jwt
        if(audience == null){
            BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
            audience = (Audience) factory.getBean("audience");
        }

        // 验证token是否有效
        try {
            Claims jwt =  JwtTokenUtil.parseJWT(token, audience.getBase64Secret());
            System.out.println(jwt);
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("token异常、请重新登录");
            errorResponse(response);
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        // System.out.println("post handle finish");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        // System.out.println("complete handle request");
    }

    public void errorResponse(HttpServletResponse response)  throws Exception{
        response.setStatus(200);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write("{\"name\":\"没有登录或者Token过期、请登录后在尝试\"}");
        response.getWriter().flush();
    }

}
