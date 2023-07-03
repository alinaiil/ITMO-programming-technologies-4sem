package kz.alinaiil.kotiki.kitties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "kz.alinaiil.kotiki.data.repositories")
@EntityScan(basePackages = "kz.alinaiil.kotiki.data.models")
@SpringBootApplication(scanBasePackages = {"kz.alinaiil.kotiki"})
public class KittyApplication {
    public static void main(String[] args) {
        SpringApplication.run(KittyApplication.class, args);
    }
}
