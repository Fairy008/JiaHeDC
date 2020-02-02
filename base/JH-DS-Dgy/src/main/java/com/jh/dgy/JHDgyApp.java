package com.jh.dgy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * description:
 * @version <1> 2018-04-27 cxw: Created.
 */
@SpringBootApplication
@EnableEurekaClient
public class JHDgyApp {

    public static  void main(String[] args){
        SpringApplication.run(JHDgyApp.class, args);
    }
}
