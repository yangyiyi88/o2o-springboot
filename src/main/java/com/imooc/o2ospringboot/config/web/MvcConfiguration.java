package com.imooc.o2ospringboot.config.web;

import com.google.code.kaptcha.servlet.KaptchaServlet;
import com.imooc.o2ospringboot.interceptor.shopadmin.ShopLoginInterceptor;
import com.imooc.o2ospringboot.interceptor.shopadmin.ShopPermissionInterceptor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;
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
        //registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/resources/");
        registry.addResourceHandler("/upload/**").addResourceLocations("file:/Users/yangkun/Pictures/image/upload/");
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

    @Value("${kaptcha.border}")
    private String border;
    @Value("${kaptcha.image.width}")
    private String width;
    @Value("${kaptcha.image.height}")
    private String height;
    @Value("${kaptcha.textproducer.font.color}")
    private String color;
    @Value("${kaptcha.textproducer.font.size}")
    private String size;
    @Value("${kaptcha.textproducer.font.names}")
    private String names;
    @Value("${kaptcha.textproducer.char.string}")
    private String string;
    @Value("${kaptcha.textproducer.char.length}")
    private String length;
    @Value("${kaptcha.noise.color}")
    private String noiseColor;

    /**
     * 配置Kaptcha验证码Servlet
     * @return
     */
    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        ServletRegistrationBean servlet = new ServletRegistrationBean(new KaptchaServlet(), "/Kaptcha");
        servlet.addInitParameter("kaptcha.border", border);//是否有边框
        servlet.addInitParameter("kaptcha.image.width", width);//图片宽度
        servlet.addInitParameter("kaptcha.image.height", height);//图片高度
        servlet.addInitParameter("kaptcha.textproducer.font.color", color);//字体颜色
        servlet.addInitParameter("kaptcha.textproducer.font.size", size);//字体大小
        servlet.addInitParameter("kaptcha.textproducer.font.names", names);//字体
        servlet.addInitParameter("kaptcha.textproducer.char.string", string);//使用哪些字符生成验证吗
        servlet.addInitParameter("kaptcha.textproducer.char.length", length);//验证码字符个数
        servlet.addInitParameter("kaptcha.noise.color", noiseColor);//干扰线的颜色
        return servlet;
    }

    /**
     * 配置spring拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //店家管理体统拦截部分
        String path = "/shopadmin/**";

        //验证是否登陆店家管理系统的拦截器
        //注册拦截器
        InterceptorRegistration shopInterceptor = registry.addInterceptor(new ShopLoginInterceptor());
        //配置拦截的路径
        shopInterceptor.addPathPatterns(path);

        //验证是否对当前店铺有操作权限的拦截器
        InterceptorRegistration shopPermissionInterceptor = registry.addInterceptor(new ShopPermissionInterceptor());
        shopPermissionInterceptor.addPathPatterns(path);
        //配置不拦截的路径
        //不拦截shoplist page 可获得可操作店铺列表shopList
        shopPermissionInterceptor.excludePathPatterns("/shopadmin/shoplist");
        shopPermissionInterceptor.excludePathPatterns("/shopadmin/getshoplist");
        //不拦截shopmanagement page 可获得当前要操作店铺currentShop
        shopPermissionInterceptor.excludePathPatterns("/shopadmin/shopmanagement");
        shopPermissionInterceptor.excludePathPatterns("/shopadmin/getshopmanagementinfo");
        //不拦截shopoperation page 创建店铺
        shopPermissionInterceptor.excludePathPatterns("/shopadmin/shopoperation");
        shopPermissionInterceptor.excludePathPatterns("/shopadmin/getshopinitinfo");
        shopPermissionInterceptor.excludePathPatterns("/shopadmin/registershop");
    }
}
