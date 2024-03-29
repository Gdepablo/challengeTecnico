package Backend.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class LoginConfiguration implements WebMvcConfigurer {

@Bean
 protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
  http.authorizeRequests().antMatchers("/login").permitAll().anyRequest().authenticated().
      and().cors().and().csrf().disable().formLogin().loginPage("/login").permitAll().and().httpBasic();
     return http.build();
}


}

