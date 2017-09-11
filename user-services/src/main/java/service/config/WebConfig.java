package service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import service.config.handler.AdminAuthInterceptor;
import service.config.handler.UserAuthInterceptor;

/**
 * Created by mujiang on 2017/9/7.
 */

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter{

    @Override
    public void addInterceptors(InterceptorRegistry registry){

        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截

        registry.addInterceptor((new AdminAuthInterceptor())).addPathPatterns("/**/users");
        registry.addInterceptor((new UserAuthInterceptor())).addPathPatterns("/**/users/**");
        super.addInterceptors(registry);
    }


}
