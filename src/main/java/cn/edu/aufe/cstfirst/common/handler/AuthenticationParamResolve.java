
package cn.edu.aufe.cstfirst.common.handler;

import cn.edu.aufe.cstfirst.common.annotation.AuthenticationParam;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhuwenwen
 * @date 2018/9/11 21:53
 */
public class AuthenticationParamResolve implements HandlerMethodArgumentResolver {
    /**
     * 是否需要处理
     *
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(AuthenticationParam.class);
    }

    /**
     * 返回身份数据
     */
    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest httpServletRequest = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        return httpServletRequest.getHeader(Constant.USERNAME);
    }
}
