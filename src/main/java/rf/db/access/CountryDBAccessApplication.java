package rf.db.access;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@SpringBootApplication
public class CountryDBAccessApplication {

    public static void main(String[] args) {
        SpringApplication.run(CountryDBAccessApplication.class, args);
    }
}