package com.mycompany.treviska;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Treviska {

    public static void main(String[] args) {
        SpringApplication.run(Treviska.class, args);
    }
}
