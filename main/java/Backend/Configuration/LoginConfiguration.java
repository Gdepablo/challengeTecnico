package Backend.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class LoginConfiguration implements WebMvcConfigurer {

  @Override
 public void addResourceHandlers(ResourceHandlerRegistry registry) {
     registry
             .addResourceHandler("/static/**")
             .addResourceLocations("classpath:/static/");
 }

@Bean
 protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
     http.authorizeRequests().antMatchers("/login").permitAll().anyRequest().authenticated().
             and().httpBasic().and().cors().disable().
             csrf().disable().
             formLogin();
     //http.authorizeRequests().antMatchers("*").permitAll();
     return http.build();
}
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*"); // Permitir solicitudes desde cualquier origen
        config.addAllowedHeader("*");
        config.addAllowedMethod("*"); // Permitir todos los métodos HTTP (GET, POST, etc.)
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}

