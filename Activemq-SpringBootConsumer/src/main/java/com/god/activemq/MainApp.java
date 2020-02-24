package com.god.activemq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 主启动类
 * @author goodtime
 * @create 2020-02-23 4:32 下午
 */
@SpringBootApplication
@EnableScheduling
@EnableJms
public class MainApp {

    public static void main(String[] args) {
        SpringApplication.run(MainApp.class,args);
    }

}
