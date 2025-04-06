package com.aib.websystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class WebsystemApplication implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(WebsystemApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(WebsystemApplication.class, args);
    }

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        logger.info("demo");
    }

    @Bean
    public CommandLineRunner demo(FruitRepository repository) {
        return (args) -> {
            repository.save(new Fruit());
        };
    }
}
