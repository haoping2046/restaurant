package com.hp.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@Slf4j
@SpringBootApplication
public class RestaurantApplication {

    public static void main(String[] args) {

        SpringApplication.run(RestaurantApplication.class, args);
//        log.info("Project start successfully!");
    }

}
