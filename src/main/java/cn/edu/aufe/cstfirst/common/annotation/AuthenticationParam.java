package cn.edu.aufe.cstfirst.common.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author zhuwenwen
 * @date 2018/9/11 21:34
 */

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthenticationParam {
    /**
     * 用户账号
     */
    @AliasFor("value")
    String username() default "";

    /**
     * 用户账号
     */
    @AliasFor("username")
    String value() default "";
}
