package com.ark.center.commodity;

import com.ark.component.web.config.CloudAppConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@MapperScan(basePackages = "com.ark.cloud.commodity.dao")
@SpringBootApplication
@EnableFeignClients(basePackages = {})
@EnableDiscoveryClient
public class Application extends CloudAppConfig {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}