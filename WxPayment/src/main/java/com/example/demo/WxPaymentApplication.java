package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author chei1
 */

@SpringBootApplication
@EnableAsync
@EnableFeignClients
@EnableDiscoveryClient
@EnableEurekaClient
public class WxPaymentApplication {

    public static void main(String[] args) {
        SpringApplication.run(WxPaymentApplication.class, args);
    }

}
