package top.yueshushu.learn.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @Author 岳建立
 * @Date 2021/11/12 23:06
 **/
@Configuration
public class DruidConfig {
    /**
     * 解决druid 日志报错：discard long time none received connection:xxx
     * */
    @PostConstruct
    public void setProperties(){
        System.setProperty("druid.mysql.usePingMethod","false");
    }

    @Value("${spring.datasource.druid.username:yuejianli}")
    private String username;
    @Value("${spring.datasource.druid.password:yuezelin}")
    private String password;

    /**
     *  主要实现WEB监控的配置处理
     */
    @Bean
    public ServletRegistrationBean druidServlet() {

        // 现在要进行druid监控的配置处理操作
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        // 白名单,多个用逗号分割， 如果allow没有配置或者为空，则允许所有访问
        servletRegistrationBean.addInitParameter("allow", "");
        // 黑名单,多个用逗号分割 (共同存在时，deny优先于allow)
        // 控制台管理用户名
        servletRegistrationBean.addInitParameter("loginUsername", username);
        // 控制台管理密码
        servletRegistrationBean.addInitParameter("loginPassword", password);
        // 是否可以重置数据源，禁用HTML页面上的“Reset All”功能
        servletRegistrationBean.addInitParameter("resetEnable", "true");
        return servletRegistrationBean ;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean() ;
        filterRegistrationBean.setFilter(new WebStatFilter());
        //所有请求进行监控处理
        filterRegistrationBean.addUrlPatterns("/*");
        //添加不需要忽略的格式信息
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.css,/druid/*");
        return filterRegistrationBean ;
    }

}


