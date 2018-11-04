
package cn.edu.aufe.cstfirst.common.handler;

import cn.edu.aufe.cstfirst.common.annotation.SkipLogon;
import cn.edu.aufe.cstfirst.handler.BlogException;
import cn.edu.aufe.cstfirst.service.UserService;
import cn.edu.aufe.cstfirst.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhuwenwen
 * @date 2018/9/10 23:25
 */
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

    private UserService userService;

    public AuthenticationInterceptor(UserService userService) {
        this.userService = userService;
    }

    /**
     * 验证身份信息
     *
     * @param request  请求信息
     * @param response 响应信息
     * @param handler  处理
     * @return 是否已经登录
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果是访问静态资源文件
        if (handler instanceof ResourceHttpRequestHandler) {
            return super.preHandle(request, response, handler);
        }
        //判断是否跳过身份验证
        if (isSkipLogon((HandlerMethod) handler)) {
            return super.preHandle(request, response, handler);
        }
        //判断用户是否已经登录
        String token = request.getHeader(Constant.TOKEN);
        Claims claims = JwtUtil.decryptKey(token);
        if (StringUtils.isNotBlank((String) claims.get(Constant.USERNAME)) && StringUtils.isNotBlank((String) claims.get(Constant.PASSWORD))) {
            Integer logon = userService.logon((String) claims.get(Constant.USERNAME), (String) claims.get(Constant.PASSWORD));
            if (logon == 1) {
                return super.preHandle(request, response, handler);
            } else {
                throw new BlogException("用户未登录，请重新登录", -1);
            }
        } else {
            throw new BlogException("请求头中没有相关用户信息", -1);
        }

    }

    /**
     * 是否跳过登录
     *
     * @param handlerMethod 处理方法
     * @return
     */
    protected boolean isSkipLogon(HandlerMethod handlerMethod) {
        if (handlerMethod.hasMethodAnnotation(SkipLogon.class)) {
            return handlerMethod.getMethodAnnotation(SkipLogon.class).value();
        } else {
            Class<?> controller = handlerMethod.getMethod().getDeclaringClass();
            if (AnnotatedElementUtils.hasAnnotation(controller, SkipLogon.class)) {
                return controller.getDeclaredAnnotation(SkipLogon.class).value();
            }
        }
        return false;
    }
}
