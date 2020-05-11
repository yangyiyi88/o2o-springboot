package com.imooc.o2ospringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class O2ospringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(O2ospringbootApplication.class, args);
    }

}
