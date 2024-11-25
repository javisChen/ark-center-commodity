package com.ark.center.product;

import com.ark.component.web.config.ArkWebConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@MapperScan(basePackages = {
        "com.ark.center.product.infra.*.gateway.db",
        "com.ark.center.product.infra.*.db",
})
@SpringBootApplication(scanBasePackages = "com.ark.center.product")
@EnableFeignClients()
@EnableDiscoveryClient
public class ProductApplication extends ArkWebConfig {

    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }

}