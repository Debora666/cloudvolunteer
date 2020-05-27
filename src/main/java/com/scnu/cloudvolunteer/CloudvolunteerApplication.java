package com.scnu.cloudvolunteer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.scnu.cloudvolunteer.dao")
public class CloudvolunteerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudvolunteerApplication.class, args);
    }

}
