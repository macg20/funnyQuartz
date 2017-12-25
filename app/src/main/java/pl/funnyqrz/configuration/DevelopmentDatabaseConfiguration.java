package pl.funnyqrz.configuration;

import ch.vorburger.mariadb4j.springframework.MariaDB4jSpringService;
import org.mariadb.jdbc.Driver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

@Configuration
@Profile("dev")
public class DevelopmentDatabaseConfiguration {

    protected static final int DB_PORT = 3310;

    @Bean(name = "mariadb4j")
    public MariaDB4jSpringService mariaDB4j() {
        MariaDB4jSpringService service = new MariaDB4jSpringService();
        service.setDefaultPort(DB_PORT);
        return service;
    }

    @Bean
    @DependsOn("mariadb4j")
    public DataSource ds() {
        SimpleDriverDataSource ds = new SimpleDriverDataSource();
        ds.setDriverClass(Driver.class);
        ds.setUrl(String.format("jdbc:mysql://localhost:%d/test", DB_PORT));
        ds.setUsername("root");
        return ds;
    }
}