package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zhenglongfei 2019-11-25.
 *
 * @VERSION 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
@RequestMapping
public class LyUserService {
    public static void main(String[] args) {
        SpringApplication.run(LyUserService.class, args);
    }

    public String index() {
        return "ly-user";
    }
}
