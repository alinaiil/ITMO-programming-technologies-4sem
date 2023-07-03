package kz.alinaiil.kotiki.owners;

import kz.alinaiil.kotiki.data.dto.OwnerCreateDto;
import kz.alinaiil.kotiki.owners.services.OwnerServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.LocalDate;

@EnableJpaRepositories(basePackages = "kz.alinaiil.kotiki.data.repositories")
@EntityScan(basePackages = "kz.alinaiil.kotiki.data.models")
@SpringBootApplication(scanBasePackages = {"kz.alinaiil.kotiki"})
public class OwnerApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(OwnerApplication.class, args);
        OwnerServiceImpl ownerService = applicationContext.getBean(OwnerServiceImpl.class);
        ownerService.createOwner(new OwnerCreateDto("Alina", LocalDate.of(2003, 4, 16)));
    }
}
