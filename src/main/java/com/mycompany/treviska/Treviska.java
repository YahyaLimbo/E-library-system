package com.mycompany.treviska;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableJpaAuditing
@EnableCaching
public class Treviska {

    public static void main(String[] args) {
        SpringApplication.run(Treviska.class, args);
    }
}
