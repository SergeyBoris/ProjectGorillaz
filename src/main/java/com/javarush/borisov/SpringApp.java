package com.javarush.borisov;

import com.javarush.borisov.util.DockerMySQLStarter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EnableJpaRepositories(basePackages = "com.javarush.borisov.db.Repository")
@EntityScan(basePackages = "com.javarush.borisov.entity")
@SpringBootApplication
public class SpringApp {
    public static void main(String[] args) {
        DockerMySQLStarter.startAndWait();
        SpringApplication.run(SpringApp.class, args);
    }
}