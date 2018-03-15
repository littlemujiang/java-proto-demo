package service.config.handler;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import service.entity.ResponseDiscription;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by mujiang on 2017/9/7.
 */
public class UserAuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        String method = httpServletRequest.getMethod();
        if("options".equalsIgnoreCase(method)){
            return true;
        }

        String accessToken = httpServletRequest.getHeader("accessToken");
        String token = httpServletRequest.getHeader("token");


        if( (accessToken != null && accessToken.equals("AccessTokenContent")) || ( token != null && token.equals("TokenContent")))
            return true;

        httpServletResponse.setStatus(401);
        httpServletResponse.getWriter().write(ResponseDiscription.InvalidAccessTokenOrToken);

        return false;

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
