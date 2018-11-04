
package cn.edu.aufe.cstfirst.common.handler;

import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author zhuwenwen
 * @date 2018/9/11 23:07
 */
public class AuthenticationConfiguration implements WebMvcConfigurer {

    /**
     * 身份拦截器
     */
    private AuthenticationInterceptor authenticationInteceptor;

    /**
     * 请求参数解析器
     */
    private AuthenticationParamResolve authenticationParamResolve;

    public AuthenticationConfiguration(AuthenticationInterceptor authenticationInteceptor,AuthenticationParamResolve authenticationParamResolve){
        this.authenticationInteceptor=authenticationInteceptor;
        this.authenticationParamResolve=authenticationParamResolve;
    }

    /**
     * 添加拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration;
        interceptorRegistration=registry.addInterceptor(authenticationInteceptor);
        //拦截配置
        interceptorRegistration.addPathPatterns("/**");
        //排除配置
//        interceptorRegistration.excludePathPatterns();
    }

    /**
     * 添加参数解析器
     *
     * @param resolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        //添加参数解析器
        resolvers.add(authenticationParamResolve);
    }

    /**
     * 添加静态资源访问
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/templates/");
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }
}
