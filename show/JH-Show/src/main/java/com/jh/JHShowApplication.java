package com.jh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
// import tk.mybatis.spring.annotation.MapperScan;

/**
 * 工程入口
 *
 * @version <1> 2018-08-02 09:44:44 Hayden : Created.
 */
@SpringBootApplication
@EnableFeignClients(basePackages={"com.jh.biz.feign"})
@EnableTransactionManagement(proxyTargetClass = true)
@EnableEurekaClient
public class JHShowApplication {
    public static void main(String[] args) {
        SpringApplication.run(JHShowApplication.class, args);
    } 
}
