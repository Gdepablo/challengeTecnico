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

@Bean
 protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
  http.authorizeRequests().antMatchers("/login").permitAll().anyRequest().authenticated().
      and().csrf().disable().formLogin().loginPage("/login").permitAll().and().httpBasic();
     return http.build();
}

}

