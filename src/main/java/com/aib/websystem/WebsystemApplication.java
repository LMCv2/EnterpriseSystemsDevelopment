package com.aib.websystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@ServletComponentScan
@SpringBootApplication
public class WebsystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebsystemApplication.class, args);
    }
}
