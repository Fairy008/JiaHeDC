package com.jh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Description:
 *
 * @version <1> 2018-07-14  lcw : Created.
 */
@SpringBootApplication
@EnableFeignClients
@EnableTransactionManagement(proxyTargetClass = true)
@EnableEurekaClient
public class JHPublishApplication
{

    public static void main(String[] args) {
        SpringApplication.run(JHPublishApplication.class, args);
    }
}
