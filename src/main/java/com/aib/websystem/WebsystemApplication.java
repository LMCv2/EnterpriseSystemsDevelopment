package com.aib.websystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;

import com.aib.websystem.entity.Fruit;
import com.aib.websystem.repository.FruitRepository;

@ServletComponentScan
@SpringBootApplication
public class WebsystemApplication {
    private static final Logger logger = LoggerFactory.getLogger(WebsystemApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(WebsystemApplication.class, args);
    }

    @Bean
    public CommandLineRunner initDatabase(FruitRepository repository) {
        logger.info("init database");
        return (args) -> {
            if (repository.count() == 0) {
                repository.save(new Fruit("Apple"));
                repository.save(new Fruit("Banana"));
                repository.save(new Fruit("Orange"));
                repository.save(new Fruit("Strawberry"));
                repository.save(new Fruit("Mango"));
            }
        };
    }
}
