package com.nastryair.project.hotelmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class HotelmanagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotelmanagerApplication.class, args);
    }
}
