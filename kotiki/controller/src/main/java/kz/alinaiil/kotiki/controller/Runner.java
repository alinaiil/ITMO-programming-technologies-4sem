package kz.alinaiil.kotiki.controller;

import kz.alinaiil.kotiki.service.services.UserServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "kz.alinaiil.kotiki.data.repositories")
@EntityScan(basePackages = "kz.alinaiil.kotiki.data.models")
@SpringBootApplication(scanBasePackages = {"kz.alinaiil.kotiki"})
public class Runner {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(Runner.class, args);
        UserServiceImpl userService = applicationContext.getBean(UserServiceImpl.class);
        userService.createUser("Alina", "123", "ADMIN", 1);
    }
}
