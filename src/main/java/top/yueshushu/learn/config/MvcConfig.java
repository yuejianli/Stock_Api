package top.yueshushu.learn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import top.yueshushu.learn.interceptor.AuthorizationInterceptor;

/**
 * @ClassName:MvcConfig
 * @Description Web端配置
 * @Author zk_yjl
 * @Date 2021/6/29 16:31
 * @Version 1.0
 * @Since 1.0
 **/
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Bean
    public HandlerInterceptor getAuthInterceptor() {
        //返回自定义的拦截类
        return new AuthorizationInterceptor();
    }
    /**
     * 配置静态的资源信息
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        //映射 static 目录
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        //favicon.ico
        registry.addResourceHandler("/favicon.ico")
                .addResourceLocations("classpath:/static/");
        //放置其他 业务页面资源
        registry.addResourceHandler("/**").addResourceLocations("classpath:/templates/");
    }
}
