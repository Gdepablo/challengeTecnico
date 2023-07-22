package Backend.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.util.List;


@Configuration
public class LoginConfiguration implements WebMvcConfigurer {
  CustomSuccessHandler customSuccessHandler;

  @Autowired
  public LoginConfiguration(CustomSuccessHandler customSuccessHandler) {
    this.customSuccessHandler = customSuccessHandler;
  }

  @Override
 public void addResourceHandlers(ResourceHandlerRegistry registry) {
     registry
             .addResourceHandler("/static/**")
             .addResourceLocations("classpath:/static/");
 }

@Bean
 protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
  http.authorizeRequests().antMatchers("/login").permitAll().anyRequest().authenticated().
      and().csrf().disable().httpBasic().and().formLogin().permitAll().and().cors().disable();
     return http.build();
}
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(List.of("*")); // Permitir solicitudes desde cualquier origen
        config.addAllowedHeader("*");
        config.addAllowedMethod("*"); // Permitir todos los métodos HTTP (GET, POST, etc.)
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}

