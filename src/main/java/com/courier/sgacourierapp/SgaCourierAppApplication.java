package com.courier.sgacourierapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SgaCourierAppApplication {

    Logger logger = LoggerFactory.getLogger(SgaCourierAppApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(SgaCourierAppApplication.class, args);
    }

}
