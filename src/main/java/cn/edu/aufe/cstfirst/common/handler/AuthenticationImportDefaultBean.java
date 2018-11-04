
package cn.edu.aufe.cstfirst.common.handler;

import cn.edu.aufe.cstfirst.common.annotation.EnableAuthentication;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;


/**
 * @author zhuwenwen
 * @date 2018/9/10 23:10
 */
public class AuthenticationImportDefaultBean implements ImportBeanDefinitionRegistrar {


    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        //创建身份认证拦截器的bean
        if (importingClassMetadata.isAnnotated(EnableAuthentication.class.getName())){
            Map<String, Object> annotationAttributes = importingClassMetadata.getAnnotationAttributes(EnableAuthentication.class.getName());
            if (annotationAttributes.get(Constant.ENABLE).equals(Boolean.TRUE)){
                BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(AuthenticationInterceptor.class);
                registry.registerBeanDefinition(AuthenticationInterceptor.class.getName(),beanDefinitionBuilder.getBeanDefinition());
            }
        }


        //创建参数处理的bean
        BeanDefinitionBuilder beanDefinitionBuilderArgu = BeanDefinitionBuilder.rootBeanDefinition(AuthenticationParamResolve.class);
        registry.registerBeanDefinition(AuthenticationParamResolve.class.getName(),beanDefinitionBuilderArgu.getBeanDefinition());

        //添加MVC相关的配置WebMvcConfig
        BeanDefinitionBuilder beanDefinitionBuilderConfig = BeanDefinitionBuilder.rootBeanDefinition(AuthenticationConfiguration.class);
        registry.registerBeanDefinition(AuthenticationConfiguration.class.getName(),beanDefinitionBuilderConfig.getBeanDefinition());
    }
}
