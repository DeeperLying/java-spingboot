package com.evan.wj.Interceptor;

import com.evan.wj.pojo.Audience;
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
        }

        // 获取token
        final String token = authHeader.substring(7);

        if(audience == null){
            BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
            audience = (Audience) factory.getBean("audience");
        }

        // 验证token是否有效--无效已做异常抛出，由全局异常处理后返回对应信息
        Claims jwt =  JwtTokenUtil.parseJWT(token, audience.getBase64Secret());

        System.out.println(jwt);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        System.out.println("post handle finish");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        System.out.println("complete handle request");
    }
}
