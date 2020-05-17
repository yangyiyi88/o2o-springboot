package com.imooc.o2ospringboot.config.web;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * WebMvcConfigurer:配置视图解析器
 * ApplicationContextAware：当一个类实现了这个接口之后，这个类就可以方便获得ApplicationContext中的所有bean(也就是spring容器中配置好的bean)
 */
@Configuration
//等价于<mvc:annotation-driven/>开启SpringMVC注解模式
@EnableWebMvc
public class MvcConfiguration implements WebMvcConfigurer, ApplicationContextAware {
    //Spring容器
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 静态资源配置
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //addResourceHandler("/resources/**").addResourceLocations("classpath:/resources/");
    }

    /**
     * 定义默认的请求处理器
     *
     * @param configurer
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    /**
     * 定义视图解析器
     *
     * @return
     */
    @Bean(name = "viewResolver")
    public InternalResourceViewResolver createInternalesourceViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        // 设置Spring 容器
        viewResolver.setApplicationContext(applicationContext);
        // 取消缓存
        viewResolver.setCache(false);
        viewResolver.setPrefix("/WEB-INF/html/");
        viewResolver.setSuffix(".html");
        return viewResolver;
    }

    /**
     * 定义文件上传解析器
     *
     * @return
     */
    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver createCommonsMultipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setDefaultEncoding("utf-8");
        //1024*1024*20 = 20M
        multipartResolver.setMaxUploadSize(20971520);
        multipartResolver.setMaxInMemorySize(20971520);
        return multipartResolver;
    }
}
