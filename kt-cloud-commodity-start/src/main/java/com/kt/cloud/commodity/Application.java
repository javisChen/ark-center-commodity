package com.kt.cloud.commodity;

import com.kt.component.web.config.CloudAppConfig;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@MapperScan(basePackages = "com.kt.cloud.commodity.dao")
@SpringBootApplication
@EnableFeignClients(basePackages = {})
@EnableDiscoveryClient
@Slf4j
public class Application extends CloudAppConfig {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}