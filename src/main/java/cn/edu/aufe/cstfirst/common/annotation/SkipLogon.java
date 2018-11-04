package cn.edu.aufe.cstfirst.common.annotation;

import java.lang.annotation.*;

/**
 * @author zhuwenwen
 * @date 2018/9/11 9:17
 */

@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SkipLogon {

    boolean value() default true;
}
