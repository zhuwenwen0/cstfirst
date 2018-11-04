package cn.edu.aufe.cstfirst.common.annotation;

import cn.edu.aufe.cstfirst.common.handler.AuthenticationImportDefaultBean;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author zhuwenwen
 * @date 2018/9/10 23:06
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(AuthenticationImportDefaultBean.class)
@Documented
public @interface EnableAuthentication {
    boolean enable() default true;

}
