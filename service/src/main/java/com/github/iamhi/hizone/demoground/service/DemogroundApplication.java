package com.github.iamhi.hizone.demoground.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.github.iamhi.hizone.demoground"})
@ConfigurationPropertiesScan("com.github.iamhi.hizone.demoground.config")
@EntityScan("com.github.iamhi.hizone.demoground.output.*")
@EnableJpaRepositories("com.github.iamhi.hizone.demoground.output.*")
public class DemogroundApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemogroundApplication.class, args);
    }
}
