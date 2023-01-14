package Backend;

import org.springframework.boot.SpringApplication;

//Parece que funciona sin el scanBasePackages entonces se lo saco.
@org.springframework.boot.autoconfigure.SpringBootApplication
public class SpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootApplication.class, args);
}}
