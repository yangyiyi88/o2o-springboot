package com.imooc.o2ospringboot.config.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.annotation.Resource;
import java.io.IOException;


@Configuration
public class SessionFactoryConfiguration {
    //mybatis-config.xml配置文件的路径
    private static String mybaitsConfigFile;

    @Value("${mybatis_config_file}")
    public void setMybaitsConfigFile(String mybaitsConfigFile) {
        SessionFactoryConfiguration.mybaitsConfigFile = mybaitsConfigFile;
    }

    //mybatis mapper xml文件所在路径
    private static String mapperPath;

    @Value("${mapper_path}")
    public void setMapperPath(String mapperPath) {
        SessionFactoryConfiguration.mapperPath = mapperPath;
    }

    //实体类所在的package
    @Value("${type_alias_package}")
    private String typeAliasesPackage;

    @Resource
    private ComboPooledDataSource dataSource;

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactoryBean createSqlSessionFactoryBean() throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        //注入数据库连接池
        sqlSessionFactoryBean.setDataSource(dataSource);
        //配置Mybatis全局配置文件：mybaits-config.xml地址
        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource(mybaitsConfigFile));
        //别名包扫名 扫描eneity包 使用别名
        sqlSessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);
        //扫描sql配置文件：mapper需要的xml文件
        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + mapperPath;
        sqlSessionFactoryBean.setMapperLocations(pathMatchingResourcePatternResolver.getResources(packageSearchPath));
        return sqlSessionFactoryBean;
    }
}
