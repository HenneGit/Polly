package com.hahrens.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@SpringBootApplication
@EntityScan(basePackages = {"com.hahrens.backend.model"})
@EnableJpaRepositories(basePackages = {"com.hahrens.backend.repository"})
@ComponentScan({"com.hahrens.*"})
public class PollyApplication {

    public static void main(String[] args) {
        System.out.println("Hello");
        SpringApplication.run(PollyApplication.class, args);
    }

}
