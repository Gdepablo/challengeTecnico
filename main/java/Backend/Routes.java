package Backend;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//Parece que funciona sin el scanBasePackages entonces se lo saco.
@org.springframework.boot.autoconfigure.SpringBootApplication
public class Routes {

    public static void main(String[] args) {
        SpringApplication.run(Routes.class, args);
}

@Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
        @Override
            public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/xx").allowedOrigins("http://localhost:4200");
        }
    };
}

}
