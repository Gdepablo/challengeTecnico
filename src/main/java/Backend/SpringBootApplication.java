package Backend;

import org.springframework.boot.SpringApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

//Parece que funciona sin el scanBasePackages entonces se lo saco.
@org.springframework.boot.autoconfigure.SpringBootApplication
@EnableWebSecurity
public class SpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootApplication.class, args);
}}
