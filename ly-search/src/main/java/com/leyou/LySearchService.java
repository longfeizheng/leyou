package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Created by zhenglongfei 2019-11-22.
 *
 * @VERSION 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class LySearchService {

    public static void main(String[] args) {
        SpringApplication.run(LySearchService.class, args);
    }
}


