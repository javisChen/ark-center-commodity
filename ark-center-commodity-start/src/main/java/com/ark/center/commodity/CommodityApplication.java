package com.ark.center.commodity;

import com.ark.component.web.config.ArkWebConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@MapperScan(basePackages = {
        "com.ark.center.commodity.infra.*.repository.db",
})
@SpringBootApplication(scanBasePackages = "com.ark.center.commodity")
@EnableFeignClients(basePackages = {})
@EnableDiscoveryClient
public class CommodityApplication extends ArkWebConfig {

    public static void main(String[] args) {
        SpringApplication.run(CommodityApplication.class, args);
    }

}