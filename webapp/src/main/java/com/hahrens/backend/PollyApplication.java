package com.hahrens.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@SpringBootApplication
@EntityScan(basePackages = {"com.hahrens.*"})
@EnableJpaRepositories(basePackages = {"com.hahrens.backend.repository"})
public class PollyApplication {

    public static void main(String[] args) {
        SpringApplication.run(PollyApplication.class, args);
    }

}
