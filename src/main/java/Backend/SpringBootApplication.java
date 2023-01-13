package Backend;

import org.springframework.boot.SpringApplication;


@org.springframework.boot.autoconfigure.SpringBootApplication(scanBasePackages = {"Backend.controller","Backend"})
public class SpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootApplication.class, args);
}}
