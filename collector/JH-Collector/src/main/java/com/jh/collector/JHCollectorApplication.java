package com.jh.collector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableFeignClients(basePackages={"com.jh.biz.feign"})
@EnableTransactionManagement(proxyTargetClass = true)
@EnableEurekaClient
public class JHCollectorApplication {
    public static void main(String[] args) {
		SpringApplication.run(JHCollectorApplication.class, args);
	}
}
