package pl.funnyqrz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import pl.funnyqrz.services.quartz.SchedulerConfig;

@EnableWebMvc
@ComponentScan(basePackages = "pl.funnyqrz")
@EnableJpaRepositories(basePackages = "pl.funnyqrz.repositories")
@EntityScan(basePackages = "pl.funnyqrz.entities")
@SpringBootApplication
@Import(SchedulerConfig.class)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
