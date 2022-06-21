package com.triple.mileage;

import org.hibernate.type.UUIDBinaryType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class MileageApplication {

    public static void main(String[] args) {
        SpringApplication.run(MileageApplication.class, args);
    }

}
