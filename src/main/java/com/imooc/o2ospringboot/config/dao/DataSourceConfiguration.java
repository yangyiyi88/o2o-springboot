package com.imooc.o2ospringboot.config.dao;

import com.imooc.o2ospringboot.util.DESUtil;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.beans.PropertyVetoException;


//配置dataSource 到ioc容器里面
@Configuration
//配置mybatis mapper的扫描路径
@MapperScan("com.imooc.o2ospringboot.dao")
public class DataSourceConfiguration {
    @Value("${jdbc.driver}")
    private String driverClass;

    @Value("${jdbc.url}")
    private String jdbcUrl;

    @Value("${jdbc.username}")
    private String user;

    @Value("${jdbc.password}")
    private String password;

    @Bean(name = "dataSource")
    public ComboPooledDataSource createComboPooledDataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        //配置连接池属性
        dataSource.setDriverClass(driverClass);
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUser(DESUtil.getDecryptString(user));
        dataSource.setPassword(DESUtil.getDecryptString(password));
        //c3p0连接池的私有属性
        dataSource.setMaxPoolSize(30);
        dataSource.setMinPoolSize(10);
        //关闭连接后不自动commit
        dataSource.setAutoCommitOnClose(false);
        //获取连接超时时间
        dataSource.setCheckoutTimeout(10000);
        //当获取连接失败重试次数
        dataSource.setAcquireRetryAttempts(2);
        return dataSource;
    }
}
