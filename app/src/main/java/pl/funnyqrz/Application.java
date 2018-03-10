package pl.funnyqrz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import pl.funnyqrz.entities.account.RoleEntity;
import pl.funnyqrz.services.account.RoleService;
import pl.funnyqrz.services.quartz.configuration.SchedulerConfig;



@EnableWebMvc
@ComponentScan(basePackages = "pl.funnyqrz")
@EnableJpaRepositories(basePackages = "pl.funnyqrz.repositories")
@EntityScan(basePackages = "pl.funnyqrz.entities")
@SpringBootApplication
@Import(SchedulerConfig.class)
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class Application implements CommandLineRunner {

   Logger logger = LoggerFactory.getLogger(Application.class);

    private RoleService roleService;

    @Autowired
    public Application(RoleService roleService) {
        this.roleService = roleService;

    }

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {

        try {
            RoleEntity roleUser = new RoleEntity();
            roleUser.setName("ROLE_USER");
            roleService.save(roleUser);

            RoleEntity roleAdmin = new RoleEntity();
            roleAdmin.setName("ROLE_ADMIN");
            roleService.save(roleAdmin);
        } catch (DataIntegrityViolationException ex) {
        logger.error("Error",ex);
    }
    }
}
