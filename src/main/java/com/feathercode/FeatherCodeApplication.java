package com.feathercode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class FeatherCodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeatherCodeApplication.class, args);
    }

}
