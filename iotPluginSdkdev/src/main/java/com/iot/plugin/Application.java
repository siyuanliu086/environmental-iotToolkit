package com.iot.plugin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
 
@SpringBootApplication
@MapperScan("com.iot.plugin.sdkdev.database")
@ComponentScan(basePackages = {"com"})
@EnableScheduling
@ServletComponentScan(basePackages = {"com"})
public class Application {
    
    public static void main(String[] args) { 
       SpringApplication.run(Application.class, args);
       System.out.println("-------------------iot.plugin.sdkdev 启动完成-------------------");
    }
}
