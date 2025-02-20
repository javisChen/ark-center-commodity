package com.ark.center.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

@MapperScan(basePackages = {
        "com.ark.center.*.infra.*.repository.db",
})
@SpringBootApplication(
        scanBasePackages = "com.ark.center.*",
        exclude = {ErrorMvcAutoConfiguration.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
